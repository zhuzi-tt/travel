package cn.ynmz.travel.dao;

import cn.ynmz.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {
    /**
     * 根据routeid查询线路图片
     * @param rid
     * @return
     */
    public List<RouteImg> findRouteImage(int rid);
}
