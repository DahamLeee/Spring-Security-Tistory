package com.wangtak.formauthentication.role.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleCode;
    private String roleDesc;

    @Builder
    public Role(String roleCode, String roleDesc) {
        this.roleCode = roleCode;
        this.roleDesc = roleDesc;
    }
}
