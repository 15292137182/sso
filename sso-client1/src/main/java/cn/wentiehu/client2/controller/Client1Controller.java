package cn.wentiehu.client2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wentiehu
 * @version 1.0
 * @date 2019/1/11
 */
@Controller
public class Client1Controller {


    @RequestMapping("client1")
    public String client() {
        return "client1";
    }
}
