package cn.ynmz.travel.web.servlet;

import cn.ynmz.travel.domain.PageBean;
import cn.ynmz.travel.domain.Route;
import cn.ynmz.travel.domain.User;
import cn.ynmz.travel.service.FavoriteService;
import cn.ynmz.travel.service.RoutService;
import cn.ynmz.travel.service.impl.FavoriteServiceImpl;
import cn.ynmz.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.IllegalFormatCodePointException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RoutService service=new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数
        String currentPages = request.getParameter("currentPage");
        String pageSizes = request.getParameter("pageSize");
        String cids = request.getParameter("cid");
        String rname = request.getParameter("rname");
        /**
         * tomcat默认为iso-8859-1编码，想要使用正确中文字符，首先将数据转换为字节数组，让后在将字节数组转换为指定编码类型的字节数组
         * getBytes("iso-8859-1"),"utf-8")
         */
        rname=new String(rname.getBytes("iso-8859-1"),"utf-8");//解决乱码问题


        int cid=0;//类别id
        /**
         * "null".equals(cids);注意不能将null值进行类型转换 Integer.parseInt(cids);，不然会报异常，一定要对拿到的值进行判断
         */
        if (cids!=null&&cids.length()>0&&!"null".equals(cids)){
             cid = Integer.parseInt(cids);

        }
        int currentPage=0;//首次访问当前页码默认为第一页
        if (currentPages!=null&&currentPages.length()>0){
            currentPage = Integer.parseInt(currentPages);
        }else {
            currentPage=1;
        }

        int pageSize=0;//每页显示条数，默认为5
        if (pageSizes!=null&&pageSizes.length()>0){
            pageSize= Integer.parseInt(pageSizes);
        }else {
            pageSize=5;
        }



        //调用service
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize,rname);
        //将数据序列化为json返回给客户端展示
        writeValue(routePageBean,response);

    }

    /**
     * 根据rid查询旅游线路详细详细
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
          Route route= service.findOne(rid);
          writeValue(route,response);
    }

    /**
     * 判断当前用户是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路id
        String rid = request.getParameter("rid");
        //获取当前登录用户user
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;//用户id
        if (user==null){
            //用户尚未登录
            uid=0;
        }else {
            //用户登录了
            uid=user.getUid();
        }

        //调用service查询
        boolean flag = favoriteService.favorite(rid, uid);
        //回写客户端
        writeValue(flag,response);

    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user =(User)request.getSession().getAttribute("user");
        int uid=0;//用户id
        if (user==null){
            //用户尚未登录
            return ;
        }else {
            //用户登录了
            uid=user.getUid();
        }
        favoriteService.addFavorite(rid,uid);

    }
}
