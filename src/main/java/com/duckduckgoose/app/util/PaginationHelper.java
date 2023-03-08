package com.duckduckgoose.app.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public final class PaginationHelper {

    public static <T> List<Integer> getPageNumbers(Page<T> page) {
        List<Integer> pageNumbers = new ArrayList<>();
        if (page.getTotalPages() == 0) {
            return pageNumbers;
        }

        int leftEnd = Math.min(2, page.getTotalPages());
        for (int i = 1; i <= leftEnd; i++) {
            pageNumbers.add(i);
        }
        if (leftEnd == page.getTotalPages()) {
            return pageNumbers;
        }

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

        int rightStart = Math.max(midEnd + 1, page.getTotalPages() - 1);
        if (rightStart > midEnd + 1) {
            pageNumbers.add(null);
        }
        for (int i = rightStart; i <= page.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }

}
