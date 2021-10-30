package cn.ynmz.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //进行方法的分发
        //1.获取请求路径
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        try {
            //获取方法对象，方法里面传入方法名称，方法参数,
            /**
             * getDeclaredMethod()忽略权限访问修饰符，来获取方法
             */
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);


            /**
             * 暴力反射
             * method.setAccessible(true);
             */
            //执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入对象进行序列化为接送，并且写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);

    }

    /**
     * 将传入的对象序列化为json，返回
     *
     * @param obj
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
         return mapper.writeValueAsString(obj);
    }
}
