package cn.ynmz.travel.service.impl;

import cn.ynmz.travel.dao.FavoriteDao;
import cn.ynmz.travel.dao.RouteDao;
import cn.ynmz.travel.dao.impl.FavoriteDaoImpl;
import cn.ynmz.travel.dao.impl.RouteDaoImpl;
import cn.ynmz.travel.domain.PageBean;
import cn.ynmz.travel.domain.Route;
import cn.ynmz.travel.service.RoutService;

import java.util.List;

public class RouteServiceImpl implements RoutService {
    private RouteDao dao=new RouteDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页条数
        pb.setPageSize(pageSize);
        //总记录数
        int totalCount = dao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示数据集合
        int start=(currentPage-1)*pageSize;//开始记录数
        List<Route> list = dao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        //设置总页数
        int totaPage= totalCount%pageSize==0 ? totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totaPage);

        return pb;
    }


    @Override
    public Route findOne(String rid) {
        Route route = dao.findOne(Integer.parseInt(rid));
       int routeCount= favoriteDao.findCountByRid(Integer.parseInt(rid));
        route.setCount(routeCount);
        return route;
    }
}
