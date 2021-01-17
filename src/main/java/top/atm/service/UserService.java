package top.atm.service;

import top.atm.constant.ErrorCode;

/**
 * @author taifu
 */

public interface UserService {
    /**
     * 通过用户输入的账户 id 获取用户姓名
     */
    String getUsernameByAccountId(String accountId);

    /**
     * 修改用户信息
     * @param id 用户 id
     * @param name 用户的姓名
     * @param address 用户的住址
     * @param phoneNumber 用户的联系方式: 电话号码
     */
    ErrorCode modifyInformation(Long id, String name, String address, String phoneNumber);
}
