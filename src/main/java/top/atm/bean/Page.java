package top.atm.bean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings ("unused")
public class Page<T> implements Serializable {
    private Integer totalItems;
    private Integer itemPerPage;
    private Integer currentPage;
    private List<T> itemList;

    public Page() {}

    public Page(Integer totalItems, Integer itemPerPage, Integer currentPage, List<T> itemList) {
        this.totalItems = totalItems;
        this.itemPerPage = itemPerPage;
        this.currentPage = currentPage;
        this.itemList = itemList;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
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
            ", itemPerPage=" + itemPerPage +
            ", currentPage=" + currentPage +
            ", itemList=" + itemList +
            '}';
    }
}
