package com.wangtak.formauthentication.config.security.provider;

import com.wangtak.formauthentication.member.adapter.out.persistence.MemberRepository;
import com.wangtak.formauthentication.member.domain.Member;
import com.wangtak.formauthentication.role.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!StringUtils.hasText(email)) {
            throw new InsufficientAuthenticationException("이메일은 필수 입력값입니다.");
        }

        Optional<Member> findMemberOptional = userRepository.findMemberEntityGraphByEmail(email);

        if (findMemberOptional.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 이메일입니다.");
        }

        Member findMember = findMemberOptional.get();

        Role userRole = findMember.getRole();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getRoleCode()));

        return new CustomUserDetails(findMember, authorities);
    }
}
