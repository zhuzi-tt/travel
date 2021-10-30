package cn.ynmz.travel.service.impl;

import cn.ynmz.travel.dao.FavoriteDao;
import cn.ynmz.travel.dao.impl.FavoriteDaoImpl;
import cn.ynmz.travel.domain.Favorite;
import cn.ynmz.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao fd=new FavoriteDaoImpl();
    @Override
    public boolean favorite(String rid, int uid) {
        Favorite favorite = fd.findByRidAndUid(Integer.parseInt(rid), uid);
        //如果favorite为null则返回false，不为null则返回true；
        return favorite !=null;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        fd.addFavorite(Integer.parseInt(rid),uid);
    }
}
