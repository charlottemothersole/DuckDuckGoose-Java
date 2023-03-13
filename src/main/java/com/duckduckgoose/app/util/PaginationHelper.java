package com.duckduckgoose.app.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public final class PaginationHelper {

    private static final int PAGE_SIZE = 5;

    public static <T> List<Integer> getPageNumbers(Page<T> page) {
        List<Integer> pageNumbers = new ArrayList<>();
        if (page.getTotalPages() == 0) {
            return pageNumbers;
        }

        // Add leftmost page numbers (page numbers 1 and 2)
        int leftEnd = Math.min(2, page.getTotalPages());
        for (int i = 1; i <= leftEnd; i++) {
            pageNumbers.add(i);
        }
        if (leftEnd == page.getTotalPages()) {
            return pageNumbers;
        }

        // Add middle page numbers (page numbers surrounding current page)
        int midStart = Math.max(leftEnd + 1, page.getNumber());
        int midEnd = Math.min(page.getNumber() + 2, page.getTotalPages());
        if (midStart > leftEnd + 1) {
            pageNumbers.add(null);
        }
        for (int i = midStart; i <= midEnd; i++) {
            pageNumbers.add(i);
        }
        if (midEnd == page.getTotalPages()) {
            return pageNumbers;
        }

        // Add rightmost page numbers (page numbers totalPages - 1 and totalPages)
        int rightStart = Math.max(midEnd + 1, page.getTotalPages() - 1);
        if (rightStart > midEnd + 1) {
            pageNumbers.add(null);
        }
        for (int i = rightStart; i <= page.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }

    public static Pageable getPageRequest(Integer pageNumber) {
        return PageRequest.of(PaginationHelper.getPageIndex(pageNumber), PAGE_SIZE);
    }

    private static int getPageIndex(Integer pageNumber) {
        return pageNumber != null ? pageNumber - 1 : 0;
    }
}
