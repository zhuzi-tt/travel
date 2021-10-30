package cn.ynmz.travel.dao;

import cn.ynmz.travel.domain.User;

public interface UserDao {

    /**
     * 查询用户
      * @param user
     * @return
     */
    User findByUsernameuser(String user);

    /**
     * 保存用户
     * @param user
     */
    public void save(User user);

    /**
     * code码激活用户
     * @return
     */
    User findByCode(String code);
    void updateStatus(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);
}
