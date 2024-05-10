package com.moin.remittance.domain.entity.member.v1;

import com.moin.remittance.domain.dto.member.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INDEX")
    private Long index;

    @Column(name = "USER_ID", unique = true, nullable = false)
    private String userId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ID_TYPE", nullable = false)
    private String idType;

    @Column(name = "ID_VALUE", nullable = false)
    private String idValue;

    public MemberEntity toEntity(MemberDTO dto) {
        return MemberEntity.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .name(dto.getName())
                .idType(dto.getIdType())
                .idValue(dto.getIdValue())
                .build();
    }
}
