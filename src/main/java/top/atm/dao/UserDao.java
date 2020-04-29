package top.atm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.User;

/**
 * @author taifu
 */

public interface UserDao {
    Logger logger = LoggerFactory.getLogger(UserDao.class);

    /**
     * 获取一个用户
     *
     * @param id 目标用户的 id
     * @return 注意目前该方式只会获取用户的 id 和 name, 其他属性暂未获取
     * 获取失败则为 null
     */
    User getUserById(Long id);
}
