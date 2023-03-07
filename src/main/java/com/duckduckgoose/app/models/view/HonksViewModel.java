package com.duckduckgoose.app.models.view;

import com.duckduckgoose.app.models.database.Honk;

import java.util.List;

public class HonksViewModel {

    private final List<Honk> honks;

    private final String search;

    private final String filter;

    public HonksViewModel(List<Honk> honks, String search, String filter) {
        this.honks = honks;
        this.search = search;
        this.filter = filter;
    }

    public List<Honk> getHonks() {
        return honks;
    }

    public String getSearch() {
        return search;
    }

    public String getFilter() {
        return filter;
    }

}
