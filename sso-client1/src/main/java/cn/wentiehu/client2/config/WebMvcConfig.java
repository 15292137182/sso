package cn.wentiehu.client2.config;

import cn.wentiehu.client2.interceptor.LoginInterceptorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/12 22:49
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptorAdapter loginInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptorAdapter).addPathPatterns("/**");
    }
}
