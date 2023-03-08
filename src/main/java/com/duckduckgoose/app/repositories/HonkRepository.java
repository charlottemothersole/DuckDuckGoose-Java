package com.duckduckgoose.app.repositories;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface HonkRepository extends JpaRepository<Honk, Long> {

    Page<Honk> findByContentContaining(String search, Pageable pageable);

    Page<Honk> findByAuthor(Member author, Pageable pageable);

    Page<Honk> findByContentContainingAndAuthor(String search, Member author, Pageable pageable);

    Page<Honk> findByAuthorIn(Set<Member> members, Pageable pageable);

    Page<Honk> findByContentContainingAndAuthorIn(String search, Set<Member> members, Pageable pageable);

}
