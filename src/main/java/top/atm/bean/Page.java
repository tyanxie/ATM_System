package top.atm.bean;

import java.util.List;

public class Page {
    private Integer totalItems;
    private Integer itemPerPage;
    private Integer currentPage;
    private List<TransactionRecord> itemList;

    public Page(Integer totalItems, Integer itemPerPage,
                Integer currentPage, List<TransactionRecord> itemList) {
        this.totalItems = totalItems;
        this.itemPerPage = itemPerPage;
        this.currentPage = currentPage;
        this.itemList = itemList;
    }

    public Page() {
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

    public List<TransactionRecord> getItemList() {
        return itemList;
    }

    public void setItemList(List<TransactionRecord> itemList) {
        this.itemList = itemList;
    }


}
