package top.atm.service;

import top.atm.constant.ErrorCode;

/**
 * @author taifu
 */

public interface UserService {
    String getUsernameByAccountId(String accountId);

    ErrorCode modifyInformation(Long id, String name, String address, String phoneNumber);
}
