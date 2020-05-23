package top.atm.dao;

import org.junit.Test;
import top.atm.bean.TransactionRecord;
import top.atm.dao.impl.TransactionRecordDaoImpl;

import java.util.List;

/**
 * @author taifu
 */
public class TransactionRecordDaoTest {

    TransactionRecordDao recordDao = TransactionRecordDaoImpl.getInstance();

    @Test
    public void getTotalRecordByAccountId() {
        Long total = recordDao.getTotalRecordByAccountId("admin");
        System.out.println("total = " + total);
    }

    @Test
    public void listRangeRecord() {
        List<TransactionRecord> recordList = recordDao.listRangeRecord("admin", 0L, 10);
        for (TransactionRecord record : recordList) {
            System.out.println(record);
        }
    }
}