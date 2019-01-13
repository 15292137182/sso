package cn.wentiehu.client2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wentiehu
 * @version 1.0
 * @date 2019/1/11
 */
@Controller
public class Client2Controller {


    @RequestMapping("client2")
    public String client() {
        return "client2";
    }
}
