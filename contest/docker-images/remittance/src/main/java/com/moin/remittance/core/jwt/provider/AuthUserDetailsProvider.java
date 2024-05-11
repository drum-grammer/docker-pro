package com.moin.remittance.core.jwt.provider;

import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class AuthUserDetailsProvider implements UserDetails {
    private final MemberEntityV2 memberEntityV2;

    /**
     * 권한 getter 메소드
     **/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberEntityV2.getIdType();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return memberEntityV2.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntityV2.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
