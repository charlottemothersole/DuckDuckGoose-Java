package com.duckduckgoose.app.repositories;

import com.duckduckgoose.app.models.database.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    List<Member> findByUsernameContaining(String search);

    List<Member> findByFollowerMembersContaining(Member member);

}
