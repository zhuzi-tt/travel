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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//删除session中的验证码
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo inof=new ResultInfo();
            inof.setFlag(false);
            inof.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);
            return;
        }else {
            Map<String, String[]> map = request.getParameterMap();
            User user=new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            UserService service=new UserServiceImpl();
            User u=service.login(user);
            request.getSession().setAttribute("user",u);
            //判断用户名密码是否正确
            ResultInfo inof=new ResultInfo();
            if (u==null){
                //用户名密码错误
                inof.setFlag(false);
                inof.setErrorMsg("用户或者密码错误");
            }else {
                //判断用户名或者密码是否激活
                if (!"Y".equals(u.getStatus())){
                    inof.setFlag(false);
                    inof.setErrorMsg("您尚未激活，请登录邮箱激活");
                }else {
                       //登录成功
                    inof.setFlag(true);
                }

            }
            //响应数据
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),inof);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
