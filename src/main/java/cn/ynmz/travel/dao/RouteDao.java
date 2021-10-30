package cn.ynmz.travel.dao;

import cn.ynmz.travel.domain.PageBean;
import cn.ynmz.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     *
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     *
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
