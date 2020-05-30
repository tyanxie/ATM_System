package top.atm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author BlessingChimes
 * @author taifu
 */

@SuppressWarnings ("unused")
public class Page<T> implements Serializable {
    private final Long totalItems;
    private final Integer totalPages;
    private final Integer itemPerPage;
    private final Integer currentPage;
    private final Long startNumber;
    private final List<T> itemList;

    private Page(Builder<T> builder) {
        this.totalItems = builder.totalItems;
        this.totalPages = builder.totalPages;
        this.itemPerPage = builder.itemPerPage;
        this.currentPage = builder.currentPage;
        this.startNumber = builder.startNumber;
        this.itemList = builder.itemList;
    }

    public static class Builder<T> {
        private Long totalItems;
        private Integer totalPages;
        private Integer itemPerPage;
        private Integer currentPage;
        private Long startNumber;
        private List<T> itemList;

        public Page<T> build() {
            return new Page<>(this);
        }

        public Builder<T> setTotalItems(Long totalItems) {
            this.totalItems = totalItems;
            return this;
        }

        public Builder<T> setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> setItemPerPage(Integer itemPerPage) {
            this.itemPerPage = itemPerPage;
            return this;
        }

        public Builder<T> setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder<T> setStartNumber(Long startNumber) {
            this.startNumber = startNumber;
            return this;
        }

        public Builder<T> setItemList(List<T> itemList) {
            this.itemList = itemList;
            return this;
        }
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getItemPerPage() {
        return itemPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public List<T> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "Page{" +
            "totalItems=" + totalItems +
            ", totalPages=" + totalPages +
            ", itemPerPage=" + itemPerPage +
            ", currentPage=" + currentPage +
            ", startNumber=" + startNumber +
            ", itemList=" + itemList +
            '}';
    }
}
