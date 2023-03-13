package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.util.PaginationHelper;
import org.springframework.data.domain.Page;

import java.util.List;

public class MemberViewModel {

    private final Member member;

    private final Page<Honk> honks;

    private final String search;

    private final List<Integer> pages;

    public MemberViewModel(Member member, Page<Honk> honks, String search) {
        this.member = member;
        this.honks = honks;
        this.search = search;
        this.pages = PaginationHelper.getPageNumbers(honks);
    }

    public Member getMember() {
        return member;
    }

    public Page<Honk> getHonks() {
        return honks;
    }

    public String getSearch() {
        return search;
    }

    public List<Integer> getPages() {
        return pages;
    }
}
