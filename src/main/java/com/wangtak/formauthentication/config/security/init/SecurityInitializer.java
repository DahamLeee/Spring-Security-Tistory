package com.wangtak.formauthentication.config.security.init;

import com.wangtak.formauthentication.config.security.config.RedisConfig;
import com.wangtak.formauthentication.config.security.config.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, RedisConfig.class);
    }
}
