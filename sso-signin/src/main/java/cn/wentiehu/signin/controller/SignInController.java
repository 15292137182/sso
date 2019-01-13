package cn.wentiehu.signin.controller;

import cn.wentiehu.signin.utils.RedisUtils;
import cn.wentiehu.signin.utils.TokenGenerator;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author wentiehu
 * @version 1.0
 * @date 2019/1/11
 */

@Controller
public class SignInController {

    @Autowired
    private RedisUtils redisUtils;

    private static List<Map<String, Object>> list = new ArrayList<>();

    public static final String USER_KEY = "user:";


    static {
        List<String> strings = Arrays.asList("zhangsan", "lisi", "wangwu");
        Map<String, Object> map;
        for (String string : strings) {
            map = new HashMap<>();
            map.put("username", string);
            map.put("password", "qwe123");
            list.add(map);
        }
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }


    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String referer = null;
        try {
            referer = request.getHeader("referer").split("\\?")[1].split("=")[1];
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> map = queryUserInfoByUserName(username);
        if (map == null) {
            return "用户不存在";
        } else {
            String passwords = (String) map.get("password");
            if (!passwords.equals(password)) {
                return "用户名或密码错误";
            }
        }
        Cookie[] cookies = request.getCookies();
        Cookie ck = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userLogin")) {
                    ck = cookie;
                    break;
                }
            }
        }
        String token = null;
        if (ck != null) {
            String value = ck.getValue();
            if (redisUtils.get(USER_KEY + value) != null) {
                token = value;
            }
        }
        if (token == null || token.isEmpty()) {
            token = TokenGenerator.generateValue();
        }
        String userToken = USER_KEY + token;
        if (redisUtils.get(userToken) == null || "".equals(redisUtils.get(userToken))) {
            redisUtils.set(userToken, JSON.toJSONString(map));
        }
        Cookie cookie = new Cookie("userLogin", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        if (referer != null) {
            return referer;
        }
        return redisUtils.get(userToken);
    }


    public static Map<String, Object> queryUserInfoByUserName(String userName) {
        for (Map<String, Object> stringObjectMap : list) {
            if (stringObjectMap.get("username").equals(userName)) {
                return stringObjectMap;
            }
        }
        return null;
    }
}
