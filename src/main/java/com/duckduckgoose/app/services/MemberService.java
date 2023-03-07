package com.duckduckgoose.app.services;

import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getMembersContaining(String search) {
        return memberRepository.findByUsernameContaining(search);
    }

    public List<Member> getFollowedMembers(Member followerMember) {
        return memberRepository.findAll();
    }

    public List<Member> getFollowedMembersContaining(String search, Member followerMember) {
        return memberRepository.findByUsernameContaining(search);
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
