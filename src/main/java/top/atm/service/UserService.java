package top.atm.service;

import top.atm.message.AbstractMessage;

/**
 * @author taifu
 */

public interface UserService {
    String getUsernameByAccountId(String accountId);

    AbstractMessage modifyInformation(Long id, String name, String address, String phoneNumber);
}
