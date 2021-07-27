package top.mylady.auth.ctrl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import top.mylady.auth.config.JwtProperties;
import top.mylady.auth.service.AuthService;
import top.mylady.auth.utils.CookieUtils;
import top.mylady.utils.dtos.ResponseResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {

    private static final Logger logger = LoggerFactory.getLogger(AuthCtrl.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权
     */
    @PostMapping("/accredit")
    public ResponseResult authentication(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){

        //登录校验
        String token = this.authService.authentication(username, password);
        logger.info("ctrl: 打印由授权中心生成的token: "+ token);

        if (StringUtils.isBlank(token)){
            logger.warn("ctrl: 警告, 生成的token为null, 返回错误");
            return ResponseResult.errorResult(404, "校验失败");
        }

        //将token写入cookie, 并指定httpOnly为true, 防止js更改
        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge(), null, true);
        logger.info("ctrl: 将token写入cookie, 并指定httpOnly为true, 防止js更改");
        return ResponseResult.okResult("ok");

    }

    /**
     * 验证用户信息
     */
    @GetMapping("/verfiry")
    public ResponseResult verifyUser(@RequestParam("LY_TOKEN") String cookie,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        return authService.verifyUserCookie(cookie, request, response);
    }


}
