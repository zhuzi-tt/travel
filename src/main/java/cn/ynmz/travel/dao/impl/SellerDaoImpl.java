package cn.ynmz.travel.dao.impl;

import cn.ynmz.travel.dao.SellerDao;
import cn.ynmz.travel.domain.Route;
import cn.ynmz.travel.domain.Seller;
import cn.ynmz.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    @Override
    public Seller findBySeller(int sid) {
      JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql="select*from tab_seller where sid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
