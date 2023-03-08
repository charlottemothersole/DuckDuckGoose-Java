package com.duckduckgoose.app.services;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.models.request.HonkRequest;
import com.duckduckgoose.app.repositories.HonkRepository;
import com.duckduckgoose.app.repositories.MemberRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HonkService {

    private final HonkRepository honkRepository;

    private final MemberRepository memberRepository;

    @Autowired
    public HonkService(HonkRepository honkRepository, MemberRepository memberRepository) {
        this.honkRepository = honkRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Honk> getHonks(Pageable pageable) {
        return honkRepository.findAllByOrderByTimestampDesc(pageable);
    }

    public Page<Honk> getHonksContaining(String search, Pageable pageable) {
        return honkRepository.findByContentContainingOrderByTimestampDesc(search, pageable);
    }

    public Page<Honk> getMemberHonks(Member author, Pageable pageable) {
        return honkRepository.findByAuthorOrderByTimestampDesc(author, pageable);
    }

    public Page<Honk> getMemberHonksContaining(String search, Member author, Pageable pageable) {
        return honkRepository.findByContentContainingAndAuthorOrderByTimestampDesc(search, author, pageable);
    }

    public Page<Honk> getFollowedMemberHonks(Member followerMember, Pageable pageable) {
        Set<Member> followedMembers = memberRepository.findByFollowerMembersContaining(followerMember);
        return honkRepository.findByAuthorInOrderByTimestampDesc(followedMembers, pageable);
    }

    public Page<Honk> getFollowedMemberHonksContaining(String search, Member followerMember, Pageable pageable) {
        Set<Member> followedMembers = memberRepository.findByFollowerMembersContaining(followerMember);
        return honkRepository.findByContentContainingAndAuthorInOrderByTimestampDesc(search, followedMembers, pageable);
    }

    public void createHonk(Member author, HonkRequest request) throws ValidationException {
        Honk honk = new Honk(author, request.getContent());
        honkRepository.save(honk);
    }

}
