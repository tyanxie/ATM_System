package top.atm.service.impl;

import top.atm.bean.Page;
import top.atm.bean.TransactionRecord;
import top.atm.dao.TransactionRecordDao;
import top.atm.dao.impl.TransactionRecordDaoImpl;
import top.atm.service.TransactionRecordService;

import java.util.List;

/**
 * @author taifu
 */

public class TransactionRecordServiceImpl implements TransactionRecordService {
    private static final TransactionRecordDao recordDao = TransactionRecordDaoImpl.getInstance();

    private static volatile TransactionRecordService instance;

    public static TransactionRecordService getInstance() {
        if (instance == null) {
            synchronized (TransactionRecordService.class) {
                if (instance == null) {
                    instance = new TransactionRecordServiceImpl();
                }
            }
        }
        return instance;
    }

    private TransactionRecordServiceImpl() {}

    @Override
    public Page<TransactionRecord> getRecord(String accountId, String currentPage, String itemPerPage) {
        if (accountId == null) {
            return null;
        }

        int currentPageInt, itemPerPageInt;
        try {
            currentPageInt = currentPage == null ? 1 : Integer.parseInt(currentPage);
            itemPerPageInt = itemPerPage == null ? ITEM_PER_PAGE_DEFAULT : Integer.parseInt(itemPerPage);
        } catch (NumberFormatException e) {
            return null;
        }

        Long totalItems = recordDao.getTotalRecordByAccountId(accountId);
        int totalPages;
        if ((long) (totalItems.intValue() / (double) itemPerPageInt) == totalItems / (double) itemPerPageInt) {
            totalPages = totalItems.intValue() / itemPerPageInt;
        } else {
            totalPages = (totalItems.intValue() / itemPerPageInt) + 1;
        }
        long startNumber = (currentPageInt - 1) * itemPerPageInt;
        List<TransactionRecord> recordList = recordDao.listRangeRecord(accountId, startNumber, itemPerPageInt);

        return new Page.Builder<TransactionRecord>()
            .setCurrentPage(currentPageInt)
            .setItemPerPage(itemPerPageInt)
            .setTotalItems(totalItems)
            .setStartNumber(startNumber)
            .setItemList(recordList)
            .setTotalPages(totalPages)
            .build();
    }
}
