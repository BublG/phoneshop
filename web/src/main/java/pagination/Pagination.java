package pagination;

import java.util.Deque;
import java.util.LinkedList;

public class Pagination {

    public static Deque<Integer> getPageNumbers(int page, int maxPage, int maxDisplayedPages) {
        Deque<Integer> pageNumbers = new LinkedList<>();
        pageNumbers.add(page);
        int nextPage = page + 1;
        int prevPage = page - 1;
        while (pageNumbers.size() < maxDisplayedPages && !(nextPage > maxPage && prevPage < 1)) {
            if (nextPage <= maxPage) {
                pageNumbers.addLast(nextPage++);
            }
            if (prevPage >= 1) {
                pageNumbers.addFirst(prevPage--);
            }
        }
        return pageNumbers;
    }
}
