package cn.ynmz.travel.dao.impl;

import cn.ynmz.travel.dao.RouteImageDao;
import cn.ynmz.travel.domain.Route;
import cn.ynmz.travel.domain.RouteImg;
import cn.ynmz.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findRouteImage(int rid) {
        String sql="select*from tab_route_img where rid=?";

        return  template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
