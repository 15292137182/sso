package cn.wentiehu.client2.interceptor;

import cn.wentiehu.client2.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/12 22:45
 */
@Component
public class LoginInterceptorAdapter extends HandlerInterceptorAdapter {

    public static final String USER_KEY = "user:";
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户访问的路径
        StringBuffer requestURL = request.getRequestURL();
        Cookie[] cookies = request.getCookies();
        Cookie ck = null;
        if (cookies==null) {
            response.sendRedirect("http://127.0.0.1:8011?redirect="+requestURL);
            return false;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("userLogin")) {
                ck = cookie;
                break;
            }
        }
        if (ck != null) {
            String value = ck.getValue();
            //String s = redisUtils.get(USER_KEY + value);
            if (redisUtils.get(USER_KEY + value) != null) {
                return true;
            }
            response.sendRedirect("http://127.0.0.1:8011?redirect="+requestURL);
            return false;
        }
        response.sendRedirect("http://127.0.0.1:8011?redirect="+requestURL);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
