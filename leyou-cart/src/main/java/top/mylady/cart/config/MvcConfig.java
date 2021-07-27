package top.mylady.cart.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.mylady.cart.interceptor.LoginInterceptor;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public LoginInterceptor loginInterceptor(){
        logger.info("cart模块, Mvc LoginInterceptor 初始化完成");
        return new LoginInterceptor(jwtProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("cart模块, Mvc addInterceptors 初始化完成");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }

    //    @Bean
    //    public FilterRegistrationBean someFilterRegistration1() {
    //        //新建过滤器注册类
    //        FilterRegistrationBean registration = new FilterRegistrationBean();
    //        // 添加我们写好的过滤器
    //        registration.setFilter( new CartFilter());
    //        // 设置过滤器的URL模式
    //        registration.addUrlPatterns("/*");
    //        return registration;
    //    }
}
