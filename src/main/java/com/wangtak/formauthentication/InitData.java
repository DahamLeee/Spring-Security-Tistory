package com.wangtak.formauthentication;

import com.wangtak.formauthentication.member.domain.Member;
import com.wangtak.formauthentication.role.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.memberInit();
    }

    @Component
    static class InitDataService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void memberInit() {
            Role role = Role.builder()
                    .roleCode("ROLE_ADMIN")
                    .roleDesc("관리자 권한")
                    .build();

            em.persist(role);

            Member member = Member.builder()
                    .email("wangtak@gmail.com")
                    .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
                    .name("왕탁이")
                    .role(role)
                    .build();

            em.persist(member);
        }
    }
}
