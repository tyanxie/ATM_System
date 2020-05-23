package top.atm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author BlessingChimes
 * @author taifu
 */

@SuppressWarnings ("unused")
public class Page<T> implements Serializable {
    private Long totalItems;
    private Integer totalPages;
    private Integer itemPerPage;
    private Integer currentPage;
    private Long startNumber;
    private List<T> itemList;

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(Integer itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Long startNumber) {
        this.startNumber = startNumber;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
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
