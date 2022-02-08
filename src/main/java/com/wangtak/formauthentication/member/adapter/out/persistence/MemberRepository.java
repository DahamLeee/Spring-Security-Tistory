package com.wangtak.formauthentication.member.adapter.out.persistence;

import com.wangtak.formauthentication.member.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "role")
    @Query("select m from Member m where m.email = :email")
    Optional<Member> findMemberEntityGraphByEmail(String email);
}
