package com.wangtak.formauthentication.config.security.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SessionMember implements Serializable {

    // Member
    private Long id;
    private String email;
    private String name;

    @Builder
    public SessionMember(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
