package com.moin.remittance.application.v1.impl;

import com.moin.remittance.application.v1.RemittanceServiceV1;
import com.moin.remittance.application.v1.WebClientServiceV1;
import com.moin.remittance.application.v1.dao.MemberDAO;
import com.moin.remittance.application.v1.dao.RemittanceDAO;
import com.moin.remittance.domain.dto.remittance.v1.*;
import com.moin.remittance.domain.dto.requestparams.RemittanceQuoteRequestParamsDTO;
import com.moin.remittance.domain.entity.remittance.v1.RemittanceQuoteEntity;
import com.moin.remittance.exception.AmountLimitExcessException;
import com.moin.remittance.exception.ExpirationTimeOverException;
import com.moin.remittance.exception.InValidPatternTypeException;
import com.moin.remittance.exception.NegativeNumberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;
import static com.moin.remittance.application.v1.util.ExchangeRateCalculator.*;

@Service
@RequiredArgsConstructor
public class RemittanceServiceImpl implements RemittanceServiceV1 {

    private final RemittanceDAO remittanceDAO;

    private final MemberDAO memberDAO;

    private final WebClientServiceV1 webClientServiceV1;


    /**
     * @param reqParamDTO 요청 쿼리 파라미터 DTO
     * @return RemittanceQuoteResponseDTO 송금 견적서 DTO
     * @throws NegativeNumberException 보내는 금액이 음수일 경우
     */
    @Override
    @Transactional
    public RemittanceQuoteResponseDTO getRemittanceQuote(RemittanceQuoteRequestParamsDTO reqParamDTO) {

        // 1. 보내는 금액 음의 정수: Error throw
        if (reqParamDTO.getAmount() < 0) {
            throw new NegativeNumberException(BAD_NEGATIVE_AMOUNT);
        }

        // 2. 외부API 호출로 환율 정보 응답 데이터 받기
        HashMap<String, ExchangeRateInfoDTO> exchangeRateInfoHashMap = webClientServiceV1.fetchExchangeRateInfoFromExternalAPI(reqParamDTO.getCodes());// 환율 정보 DTO

        /* 3. 외부 API 환율 정보 요청
         * - 만료 기간 = 생성시간 + 10분
         * - 수수료 = 보내는금액(amount: 원화) * 수수료율 + 고정 수수료 => 소수점 반올림(int 형)
         * - 받는 금액 = (보내는 금액 - 수수료) / 환율
         * @param amount 원화
         * @param base price USD 환율
         * @param currency code 환율 코드
         * */
        ExchangeRateInfoDTO usdExRateDTO = exchangeRateInfoHashMap.get("USD");// USD
        ExchangeRateInfoDTO targetExRateDTO = exchangeRateInfoHashMap.get(reqParamDTO.getTargetCurrency()); // target currency

        // 4. 송금 견적서에 넣을 데이터 연산 -> 송금 견적서 DTO 생성
        double usdAmount = calculateExchangeRate(reqParamDTO.getAmount(), usdExRateDTO.getCurrencyUnit(), usdExRateDTO.getBasePrice(), usdExRateDTO.getCurrencyCode());// 원화를 달러로 환산
        double fee = calculateFee(reqParamDTO.getAmount());// 수수료 계산
        double targetAmount = calculateExchangeRate(reqParamDTO.getAmount(), targetExRateDTO.getCurrencyUnit(), targetExRateDTO.getBasePrice(), targetExRateDTO.getCurrencyCode()); // 받는 금액
        if (targetAmount < 0.0) {
            throw new NegativeNumberException(BAD_NEGATIVE_TARGET_AMOUNT);
        }
        OffsetDateTime expireTime = calculateExpireTime(10); // 송금 견적서 만료 시간 계산

        RemittanceQuoteDTO dto = new RemittanceQuoteDTO(usdExRateDTO, targetExRateDTO);// 송금 견적서 DTO
        dto.setSourceAmount(reqParamDTO.getAmount());// 원화
        dto.setFee(fee);// 수수료
        dto.setUsdAmount(usdAmount); // USD 송금액
        dto.setTargetAmount(targetAmount); // 받는 금액
        dto.setExpireTime(expireTime); // 송금 견적서 만료 기간

        // 5. 송금 견적서 저장(DB)
        RemittanceQuoteEntity remittanceQuoteEntity = remittanceDAO.saveRemittanceQuote(dto);

        return RemittanceQuoteResponseDTO.of(remittanceQuoteEntity);
    }

    @Override
    @Transactional
    public void requestRemittanceAccept(long quoteId, String userId) {

        // 1. 유저아이디와 매칭된 송금 거래 이력에서 날짜가 오늘 날짜랑 일치하는 것만 조회해서 송금액 싹 더함
        long sumOfSourceAmount = remittanceDAO.getSumOfSourceAmount(userId);// 오늘 보낸 총 송금액
        String memberIdType = memberDAO.getIdTypeByUserId(userId);// 회원 타입

        // 회원 타입이 저장 안돼 있을 경우 InValidPatternTypeException
        if(!memberIdType.equalsIgnoreCase("REG_NO") && !memberIdType.equalsIgnoreCase("BUSINESS_NO")) {
            throw new InValidPatternTypeException(BAD_NOT_FOUND_ID_TYPE);
        }

        double usdExchangeRate = webClientServiceV1.fetchExchangeRateInfoFromExternalAPI("FRX.KRWUSD").get("USD").getBasePrice();// 지금 현재 달러 환율 -> 외부 API
        double usdSourceAmountNotToFee = calculateExchangeRate(sumOfSourceAmount, usdExchangeRate);// 수수료없는 순수 원화를 달러로 환산

        // 2. 유저의 보낸금액의 총합이 이미 한도액을 넘었는지 비교
        // 개인 회원 $1000, 법인 회원 $5000
        if (usdSourceAmountNotToFee > 1000.0 && memberIdType.equalsIgnoreCase("REG_NO")) {
            throw new AmountLimitExcessException(BAD_INDIVIDUAL_MEMBER_LIMIT_EXCESS);
        }

        if(usdSourceAmountNotToFee > 5000.0 && memberIdType.equalsIgnoreCase("BUSINESS_NO")) {
            throw new AmountLimitExcessException(BAD_CORPORATION_MEMBER_LIMIT_EXCESS);
        }

        // 3. 채번한 견적서 id와 일치하는 견적서 조회
        RemittanceQuoteDTO quoteDTO = remittanceDAO.findRemittanceQuoteByQuoteId(quoteId);
        double usdAmountNotToFee = calculateExchangeRate(quoteDTO.getSourceAmount(), quoteDTO.getUsdExchangeRate());// 견적서에 기록된 송금액에 수수료 적용 안된 달러 환율 적용

        // 4. 현재금액과 보낸금액의 합을 한도액과 비교 -> 현재 환율이랑 견적서에 찍혔던 USD 환율이 다르기 때문에 따로 분류해서 구함
        // 개인 회원 $1000, 법인 회원 $5000
        if (usdSourceAmountNotToFee + usdAmountNotToFee > 1000.0 && memberIdType.equalsIgnoreCase("REG_NO")) { // 견적서에 있는 달러 환율 적용
            throw new AmountLimitExcessException(BAD_INDIVIDUAL_MEMBER_LIMIT_EXCESS);
        }

        // 5. 만료 기간 체크
        if (isExpirationTimeOver(quoteDTO.getExpireTime())) {         // 만료 시간 체크
            throw new ExpirationTimeOverException(BAD_QUOTE_EXPIRATION_TIME_OVER);
        }

        /* 6. 송금 견적서 데이터 송금 요청 이력으로 저장
         * @param remittanceQuoteDTO 송금 견적서 DTO
         * @param userId 유저 아이디
         * @param requestedDate 요청 날짜
         * */
        RemittanceLogDTO log = new RemittanceLogDTO(quoteDTO, userId, OffsetDateTime.now());// 거래 이력에 송금할 견적서와 유저 아이디 요청날짜 저장할 데이터
        remittanceDAO.saveRemittanceLog(log);
    }

    @Override
    @Transactional
    public TransactionLogDTO getRemittanceLogList(String userId) {
        // 1. userId와 일치하는 송금 거래 이력 조회
        List<RemittanceHistoryDTO> log = remittanceDAO.findRemittanceLogListByUserId(userId);

        // 2. 유저 아이디와 일치하는 유저 이름
        String name = memberDAO.getNameOfMemberByUserId(userId);

        // 3. 송금횟수랑 송금금액은 테이블 레코드 합산으로 처리 => 돈과 관련된 로직이므로 횟수를 디비에 저장하는거보다 레코드 카운트로 세는게 더 정확하다 판단
        return new TransactionLogDTO(log, userId, name);
    }

}
