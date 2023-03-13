package com.duckduckgoose.app.repositories;

import com.duckduckgoose.app.models.database.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    Page<Member> findByUsernameContaining(String search, Pageable pageable);

    Set<Member> findByFollowerMembersContaining(Member member);

    Page<Member> findByFollowerMembersContaining(Member member, Pageable pageable);

    Page<Member> findByUsernameContainingAndFollowerMembersContaining(String search, Member member, Pageable pageable);
}
