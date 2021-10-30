package cn.ynmz.travel.dao.impl;

import cn.ynmz.travel.dao.FavoriteDao;
import cn.ynmz.travel.dao.RouteDao;
import cn.ynmz.travel.dao.RouteImageDao;
import cn.ynmz.travel.dao.SellerDao;
import cn.ynmz.travel.domain.Route;
import cn.ynmz.travel.domain.RouteImg;
import cn.ynmz.travel.domain.Seller;
import cn.ynmz.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    private RouteImageDao routeImageDao=new RouteImageDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();


    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql="select count(*) from tab_route where cid=?";
        //定义sql来实现组合查询
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
     List params = new ArrayList<>();//传入参数
        if (cid!=0){
            sb.append(" and cid=?");
            params.add(cid);//添加问号对应的值
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");//添加问号对应的值
        }
         sql = sb.toString();
        /**
         * System.out.println(sql);检查sql语句拼接，不能将值为null的sql语句拼接进来拼接进
         * System.out.println(params);检查输入的值，不能将null值拼接进值里面，注意！！！！！！！！！！！！！！！！1！！！！！！！！！！！！！
         */
        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());
        return totalCount;
    }

    /**
     * 分页查询
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql="select*from tab_route where cid = ? limit ? , ?";
        String sql="select*from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList<>();//传入参数
        if (cid!=0){
            sb.append(" and cid=?");
            params.add(cid);//添加问号对应的值
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");//添加问号对应的值
        }

        sb.append(" limit ?, ? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        List<Route> Page = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return Page;
    }

    @Override
    public Route findOne(int rid) {
        String sql="select*from tab_route where rid=?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        List<RouteImg> routeImage = routeImageDao.findRouteImage(rid);//调用RouteImageDaoImpl()查询出路线的图片信息
        //将集合设置到route对象里面
        route.setRouteImgList(routeImage);
        //根据route的sid查询卖家的信息
        Seller bySeller = sellerDao.findBySeller(route.getSid());
        route.setSeller(bySeller);
        return route;
    }

}
