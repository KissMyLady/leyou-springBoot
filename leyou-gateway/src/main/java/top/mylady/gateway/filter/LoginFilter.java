package top.mylady.gateway.filter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.mylady.auth.utils.JwtUtils;
import top.mylady.gateway.config.JwtProperties;
import top.mylady.utils.CookieUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Component
public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private final List<String> allowPaths = new ArrayList<>();

    //Ulr路径白命令
    public List<String> getAllowPaths(){
        allowPaths.add("/api/auth");
        allowPaths.add("/api/search");
        allowPaths.add("/api/user/register");
        allowPaths.add("/api/user/check");
        allowPaths.add("/api/user/code");
        allowPaths.add("/api/item");
        return allowPaths;
    }


    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    /**
     * 放行白名单
     */
    @Override
    public boolean shouldFilter() {

        //获取应用上下文
        RequestContext context = RequestContext.getCurrentContext();
        //从上下文对象中获取请求对象
        HttpServletRequest request = context.getRequest();
        //获取路径
        String requestUrl = request.getRequestURI();

        //判断白名单是否放行
        for (String item: getAllowPaths()){
            if (requestUrl.startsWith(item)){
                return false;
            }
        }
        return true;
    }

    /**
     * 过滤逻辑
     */
    @Override
    public Object run() throws ZuulException {
        //获取应用上下文
        RequestContext context = RequestContext.getCurrentContext();

        //从上下文对象中获取请求对象
        HttpServletRequest request = context.getRequest();

        //获取token
        //String token = request.getParameter("access-token");
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        try {
            //校验token
            JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            logger.info("zuul, filter 校验token, 校验通过, 放行");
        }
        catch (Exception e){
            System.out.println("网关过滤器token错误, 原因e: "+ e);
        }

        return null;
    }
}
