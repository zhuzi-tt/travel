package cn.ynmz.travel.service;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean favorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(String rid, int uid);

}
