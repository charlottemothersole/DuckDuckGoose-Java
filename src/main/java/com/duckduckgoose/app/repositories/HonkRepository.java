package com.duckduckgoose.app.repositories;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface HonkRepository extends JpaRepository<Honk, Long> {

    Page<Honk> findAllByOrderByTimestampDesc(Pageable pageable);

    Page<Honk> findByContentContainingOrderByTimestampDesc(String search, Pageable pageable);

    Page<Honk> findByAuthorOrderByTimestampDesc(Member author, Pageable pageable);

    Page<Honk> findByContentContainingAndAuthorOrderByTimestampDesc(String search, Member author, Pageable pageable);

    Page<Honk> findByAuthorInOrderByTimestampDesc(Set<Member> members, Pageable pageable);

    Page<Honk> findByContentContainingAndAuthorInOrderByTimestampDesc(String search, Set<Member> members, Pageable pageable);
}
