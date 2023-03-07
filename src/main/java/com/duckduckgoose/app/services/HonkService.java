package com.duckduckgoose.app.services;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.models.request.HonkRequest;
import com.duckduckgoose.app.repositories.HonkRepository;
import com.duckduckgoose.app.repositories.MemberRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonkService {

    private final HonkRepository honkRepository;

    private final MemberRepository memberRepository;

    @Autowired
    public HonkService(HonkRepository honkRepository, MemberRepository memberRepository) {
        this.honkRepository = honkRepository;
        this.memberRepository = memberRepository;
    }

    public List<Honk> getHonks() {
        return honkRepository.findAll();
    }

    public List<Honk> getHonksContaining(String search) {
        return honkRepository.findByContentContaining(search);
    }

    public List<Honk> getMemberHonks(Member author) {
        return honkRepository.findByAuthor(author);
    }

    public List<Honk> getMemberHonksContaining(String search, Member author) {
        return honkRepository.findByContentContainingAndAuthor(search, author);
    }

    public List<Honk> getFollowedMemberHonks(Member followerMember) {
        List<Member> followedMembers = memberRepository.findByFollowerMembersContaining(followerMember);
        return honkRepository.findByAuthorIn(followedMembers);
    }

    public List<Honk> getFollowedMemberHonksContaining(String search, Member followerMember) {
        List<Member> followedMembers = memberRepository.findByFollowerMembersContaining(followerMember);
        return honkRepository.findByContentContainingAndAuthorIn(search, followedMembers);
    }

    public void createHonk(Member author, HonkRequest request) throws ValidationException {
        Honk honk = new Honk(author, request.getContent());
        honkRepository.save(honk);
    }

}
