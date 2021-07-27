package top.mylady.cart.interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.mylady.cart.config.JwtProperties;
import top.mylady.utils.pojos.auth.UserInfo;
import top.mylady.utils.auth.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;


public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private JwtProperties jwtProperties;

    /**
     * 定义一个线程域，存放登录用户
     */
    private static final ThreadLocal<UserInfo> t1 = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //1.查询token
        String token = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        if (StringUtils.isBlank(token)){
            //2.未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            logger.warn("进入到LoginInterceptor MVC Servlet字段审核阶段, 未登录，返回401 |->一律先返回true");
            return true;
        }
        //3.有token，查询用户信息
        try{
            //4.解析成功，说明已经登录
            UserInfo userInfo;
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //5.放入线程域
            t1.set(userInfo);
            logger.warn("进入到LoginInterceptor MVC Servlet字段审核阶段, 解析成功，说明已经登录 |->一律先返回true, 打印解析的userInfo字段: "+ userInfo);
            return true;
        }catch (Exception e){
            //6.抛出异常，证明未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        //防止内存泄漏
        t1.remove();
        super.afterCompletion(request, response, handler, ex);
    }

    public static UserInfo getLoginUser(){
        return t1.get();
    }
}
