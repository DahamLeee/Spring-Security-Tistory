package com.wangtak.formauthentication.config.security.config;

import com.wangtak.formauthentication.config.security.handler.FormAuthenticationFailureHandler;
import com.wangtak.formauthentication.config.security.handler.FormAuthenticationSuccessHandler;
import com.wangtak.formauthentication.config.security.provider.CustomUserDetailsService;
import com.wangtak.formauthentication.config.security.provider.FormAuthenticationProvider;
import com.wangtak.formauthentication.config.security.session.SecuritySessionExpiredStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {

    private final FormAuthenticationSuccessHandler formSuccessHandler;
    private final FormAuthenticationFailureHandler formFailureHandler;

    private final FindByIndexNameSessionRepository<S> sessionRepository;
    private final SecuritySessionExpiredStrategy securitySessionExpiredStrategy;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement(s -> s
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry())
                        .maxSessionsPreventsLogin(false)
                        .expiredSessionStrategy(securitySessionExpiredStrategy))
                .authorizeRequests(a ->
                        a.anyRequest().authenticated())
                .formLogin(f -> f
                        .defaultSuccessUrl("/")
//                        .loginPage("/login") // Custom Login Page 를 구현 할 경우
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/login")
                        .successHandler(formSuccessHandler)
                        .failureHandler(formFailureHandler)
                        .permitAll());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider(userDetailsService);
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

}
