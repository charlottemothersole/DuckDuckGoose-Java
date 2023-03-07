package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Member;

import java.util.List;

public class MembersViewModel {

    private final List<Member> members;

    private final String search;

    private final String filter;

    public MembersViewModel(List<Member> members, String search, String filter) {
        this.members = members;
        this.search = search;
        this.filter = filter;
    }

    public List<Member> getMembers() {
        return members;
    }

    public String getSearch() {
        return search;
    }

    public String getFilter() {
        return filter;
    }

}
