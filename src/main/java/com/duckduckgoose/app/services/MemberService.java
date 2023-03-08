package com.duckduckgoose.app.services;

import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Page<Member> getMembersContaining(String search, Pageable pageable) {
        return memberRepository.findByUsernameContaining(search, pageable);
    }

    public Page<Member> getFollowedMembers(Member followerMember, Pageable pageable) {
        return memberRepository.findByFollowerMembersContaining(followerMember, pageable);
    }

    public Page<Member> getFollowedMembersContaining(String search, Member followerMember, Pageable pageable) {
        return memberRepository.findByUsernameContainingAndFollowerMembersContaining(search, followerMember, pageable);
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void addFollower(Member followerMember, Member followedMember) {
        followerMember.getFollowedMembers().add(followedMember);
        memberRepository.save(followerMember);
    }

    public void removeFollower(Member followerMember, Member followedMember) {
        followerMember.getFollowedMembers().remove(followedMember);
        memberRepository.save(followerMember);
    }

}
