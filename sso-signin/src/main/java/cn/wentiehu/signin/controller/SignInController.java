package cn.wentiehu.signin.controller;

import cn.wentiehu.signin.utils.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author wentiehu
 * @version 1.0
 * @date 2019/1/11
 */

@Controller
public class SignInController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }


    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("password",password);
        return JSON.toJSONString(hashMap);
    }
}
