package cn.ynmz.travel.dao.impl;
import cn.ynmz.travel.dao.FavoriteDao;
import cn.ynmz.travel.domain.Favorite;
import cn.ynmz.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql="select*from tab_favorite where rid=? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql="select count(*) from tab_favorite where rid = ?";

        return  template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql="insert into tab_favorite value (?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
