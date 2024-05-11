package com.moin.remittance.domain.entity.member.v2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Component
@Entity
@Table(name = "member_v2")
public class MemberEntityV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "INDEX")
    private UUID index;

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

    @Override
    public String toString() {
        return "MemberEntityV2 {" + "\n" +
                "\tindex: " + index + ",\n" +
                "\tuserId: '" + userId + '\''+ ",\n" +
                "\tpassword: '" + password+ '\'' + ",\n" +
                "\tname: '" + name+ '\'' + ",\n" +
                "\tidType: '" + idType+ '\'' + ",\n" +
                "\tidValue: '" + idValue + '\'' + ",\n" +
                '}';
    }
}
