package cn.ynmz.travel.dao;

import cn.ynmz.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 查询是否被收藏
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);
}
