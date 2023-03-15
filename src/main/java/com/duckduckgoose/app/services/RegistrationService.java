package com.duckduckgoose.app.services;

import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.models.request.RegistrationRequest;
import com.duckduckgoose.app.repositories.MemberRepository;
import com.duckduckgoose.app.util.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final MemberRepository memberRepository;

    private final AuthHelper authHelper;

    @Autowired
    public RegistrationService(MemberRepository memberRepository, AuthHelper authHelper) {
        this.memberRepository = memberRepository;
        this.authHelper = authHelper;
    }

    public void registerMember(RegistrationRequest request) {
        String encodedPassword = authHelper.getPasswordEncoder().encode(request.getPassword());
        memberRepository.save(new Member(request.getUsername(), encodedPassword));
    }
}
