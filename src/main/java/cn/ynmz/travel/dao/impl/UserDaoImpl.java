package cn.ynmz.travel.dao.impl;

import cn.ynmz.travel.dao.UserDao;
import cn.ynmz.travel.domain.User;
import cn.ynmz.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsernameuser(String username) {
       User user=null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }

        return user;
    }

    @Override
    public void save(User user) {
        //定义sql
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)"+
                "values(?,?,?,?,?,?,?,?,?)";

        template.update(sql,user.getUsername(),user.getPassword(),
                            user.getName(),user.getBirthday(),user.getSex(),
                            user.getTelephone(),user.getEmail(),
                            user.getStatus(),user.getCode());

    }

    @Override
    public User findByCode(String code) {
        String sql="select*from tab_user where code=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改用户激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status = 'Y' where uid=?";
        template.update(sql,user.getUid());
    }

    @Override
    public User login(User user) {
        String sql="select*from tab_user where username= ? and password = ?";
        User loginuser = null;
        try {
            loginuser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return loginuser;
    }
}
