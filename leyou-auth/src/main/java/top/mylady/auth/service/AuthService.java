package top.mylady.auth.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.auth.client.UserClient;
import top.mylady.auth.config.JwtProperties;
import top.mylady.utils.pojos.auth.UserInfo;
import top.mylady.auth.utils.CookieUtils;
import top.mylady.auth.utils.JwtUtils;
import top.mylady.user.pojos.User;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    /**
     * 校验
     */
    public String authentication(String username, String password) {
        logger.info("开始由授权中心的service校验authentication服务, 校验用户名和密码后返回token");
        String token = null;
        logger.info("service: 打印传递过来的username: "+ username +" password: "+ password);

        try{
            //1.调用微服务查询用户信息
            User user = this.userClient.findUserByName(username, password);

            logger.info("auth调用user模块的查询服务, 打印查询到的user: "+ user);

            //2.查询结果为空，则直接返回null
            if (user == null){
                return null;
            }
            //3.查询结果不为空，则生成token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            logger.info("打印设置的userInfo: "+ userInfo);
            token = JwtUtils.generateToken(userInfo, properties.getPrivateKey(), properties.getExpire());

        }catch (Exception e){
            logger.warn("警告, 权限检验中心生成token错误, 原因: "+ e);
        }
        return token;
    }

    /**
     * 获取用户传递过来的cookie, 校验
     */
    public ResponseResult verifyUserCookie(String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());

            //刷新用户的token
            token = JwtUtils.generateToken(
                    userInfo,
                    this.properties.getPrivateKey(),
                    this.properties.getExpire()
            );

            //设置cookie, 注意, 在前端, 这个set-cookie信息显示在Headers中
            CookieUtils.setCookie(request, response,
                    properties.getCookieName(), token,
                    properties.getCookieMaxAge(),
                    null, true);

            logger.info("打印verifyUserCookie刷新后的token: "+ token);
            return ResponseResult.okResult(userInfo, "用户认证成功, 并且刷新了token");
        }

        catch (Exception e){
            System.out.println("cookie校验失败, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

    }

}
