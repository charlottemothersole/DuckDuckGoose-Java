package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;

import java.util.List;

public class MemberViewModel {

    private final Member member;

    private final List<Honk> honks;

    private final String search;

    public MemberViewModel(Member member, List<Honk> honks, String search) {
        this.member = member;
        this.honks = honks;
        this.search = search;
    }

    public Member getMember() {
        return member;
    }

    public List<Honk> getHonks() {
        return honks;
    }

    public String getSearch() {
        return search;
    }

}
