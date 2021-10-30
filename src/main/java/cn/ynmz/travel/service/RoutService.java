package cn.ynmz.travel.service;

import cn.ynmz.travel.domain.PageBean;
import cn.ynmz.travel.domain.Route;

public interface RoutService {
    /**
     * 查询分页
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(int cid,int currentPage, int pageSize ,String rname);

    /**
     * 查询路线详情
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
