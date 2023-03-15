package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.util.PaginationHelper;
import org.springframework.data.domain.Page;

import java.util.List;

public class MembersViewModel {

    private final Page<Member> members;

    private final String search;

    private final String filter;

    private final List<Integer> pages;

    public MembersViewModel(Page<Member> members, String search, String filter) {
        this.members = members;
        this.search = search;
        this.filter = filter;
        this.pages = PaginationHelper.getPageNumbers(members);
    }

    public Page<Member> getMembers() {
        return members;
    }

    public String getSearch() {
        return search;
    }

    public String getFilter() {
        return filter;
    }

    public List<Integer> getPages() {
        return pages;
    }
}
