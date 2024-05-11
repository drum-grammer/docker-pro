package com.moin.remittance.domain.dto.member;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    /*
    * @NotEmpty - null, ""을 허용하지 않는다. " "는 허용한다.
    * @NotNull - null만 허용하지 않는다. "", " "는 허용한다.
    * @NotBlank - null, "", " "을 모두 허용하지 않는다.
    * */
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "어떤 회원으로 가입을 원하시는지 선택해주세요.")
    @Pattern(
            regexp = "(?i)REG_NO|BUSINESS_NO",
            message = "REG_NO or BUSINESS_NO로 구분해주세요.")
    private String idType;

    @NotBlank(message = "개인 회원은 주민등록번호, 법인 회원은 사업자번호가 필수 입니다.")
    private String idValue;
}
