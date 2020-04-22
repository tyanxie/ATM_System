package top.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.User;

/**
 * @author taifu
 */

public interface UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 获取一个用户
     *
     * @return 直接调用了 dao 层的方法
     */
    User getUser(Long id);
}
