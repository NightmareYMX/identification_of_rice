package identification_of_rice.interceptor;

import com.school.identification_of_rice.pojo.Admin;
import com.school.identification_of_rice.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("sessionuser");
        Admin admin = (Admin)request.getSession().getAttribute("sessionadmin");
        if(user != null || admin != null){
            return true;
        }
        request.getRequestDispatcher("/user_login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
