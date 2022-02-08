package com.wangtak.formauthentication.config.security.provider;

import com.wangtak.formauthentication.member.domain.Member;
import com.wangtak.formauthentication.config.security.session.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        Member loginMember = userDetails.getMember();

        if (!StringUtils.hasText(password)) {
            throw new InsufficientAuthenticationException("비밀번호를 입력해주세요.");
        } else if (!BCrypt.checkpw(password, userDetails.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호를 입력하셨습니다.");
        }

        SessionMember sessionMember = SessionMember.builder() // 세션 저장 객체 [세션에는 필요한 정보만 저장]
                .id(loginMember.getId()) // PK
                .email(loginMember.getEmail()) // email
                .name(loginMember.getName()) // 이름
                .build();

        // 여기에 있는 sessionMember 객체가 Redis 에 저장됨.
        return new UsernamePasswordAuthenticationToken(sessionMember, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
