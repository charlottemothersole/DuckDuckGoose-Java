package com.duckduckgoose.app.repositories;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HonkRepository extends JpaRepository<Honk, Long> {

    List<Honk> findByContentContaining(String search);

    List<Honk> findByAuthor(Member author);

    List<Honk> findByContentContainingAndAuthor(String search, Member author);

    List<Honk> findByAuthorIn(List<Member> members);

    List<Honk> findByContentContainingAndAuthorIn(String search, List<Member> members);

}
