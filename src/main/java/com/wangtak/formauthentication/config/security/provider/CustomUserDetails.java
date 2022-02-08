package com.wangtak.formauthentication.config.security.provider;

import com.wangtak.formauthentication.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Member member;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
    }
}
