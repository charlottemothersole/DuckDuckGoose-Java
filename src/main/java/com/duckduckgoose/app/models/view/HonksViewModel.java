package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.util.PaginationHelper;
import org.springframework.data.domain.Page;

import java.util.List;

public class HonksViewModel {

    private final Page<Honk> honks;

    private final String search;

    private final String filter;

    private final List<Integer> pages;

    public HonksViewModel(Page<Honk> honks, String search, String filter) {
        this.honks = honks;
        this.search = search;
        this.filter = filter;
        this.pages = PaginationHelper.getPageNumbers(honks);
    }

    public Page<Honk> getHonks() {
        return honks;
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
