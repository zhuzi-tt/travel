package cn.ynmz.travel.web.servlet;

import cn.ynmz.travel.domain.ResultInfo;
import cn.ynmz.travel.domain.User;
import cn.ynmz.travel.service.UserService;
import cn.ynmz.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //声明一个userservice，进行方法抽取
    private  UserService service = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//删除session中的验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo inof = new ResultInfo();
            inof.setFlag(false);
            inof.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);
            return;
        } else {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //UserService service = new UserServiceImpl();
            boolean flag = service.regist(user);
            ResultInfo inof = new ResultInfo();

            if (flag) {
                //注册成功
                inof.setFlag(true);

            } else {
                //注册失败
                inof.setFlag(false);
                inof.setErrorMsg("该用户名已经存在，注册失败");

            }
            //将info对象序列化为json，写回客户端
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);

        }


    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//删除session中的验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo inof = new ResultInfo();
            inof.setFlag(false);
            inof.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);
            return;
        } else {
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //UserService service = new UserServiceImpl();
            User u = service.login(user);
            request.getSession().setAttribute("user", u);
            //判断用户名密码是否正确
            ResultInfo inof = new ResultInfo();
            if (u == null) {
                //用户名密码错误
                inof.setFlag(false);
                inof.setErrorMsg("用户或者密码错误");
            } else {
                //判断用户名或者密码是否激活
                if (!"Y".equals(u.getStatus())) {
                    inof.setFlag(false);
                    inof.setErrorMsg("您尚未激活，请登录邮箱激活");
                } else {
                    //登录成功
                    inof.setFlag(true);
                }

            }
            //响应数据
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), inof);
        }
    }

    /**
     * 用户登录主界面，欢迎用户信息提示功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");//json返回数据格式设定
        mapper.writeValue(response.getOutputStream(), user);

    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();//销毁session
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code != null) {
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //判断激活
            if (flag) {
                //激活成功
                response.sendRedirect("http://192.168.162.133/active_ok.html");

            } else {
                //激活失败
                response.sendRedirect("http://192.168.162.133/active_false.html");


            }
        }
    }

}
