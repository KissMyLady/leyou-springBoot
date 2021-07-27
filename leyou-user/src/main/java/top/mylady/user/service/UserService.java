package top.mylady.user.service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.mylady.user.mappers.user.IUser;
import top.mylady.user.pojos.User;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import java.util.Date;
import static org.apache.hadoop.hbase.trace.HBaseHTraceConfiguration.KEY_PREFIX;
import top.mylady.utils.CodecUtils;
import org.springframework.http.ResponseEntity;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUser iUser;

    //@Autowired
    //private StringRedisTemplate stringRedisTemplate;

    /**
     * 服务层, 用户注册
     */
    public ResponseResult registerService(User user, String code){
//        //数据校验
//        try {
//            user.setCreated(new Date());
//            user.setSalt("1");
//            int b = iUser.register(user);
//        }
//        catch (Exception e){
//            System.out.println("注册: 用户插入错误, 原因e: "+ e);
//            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
//        }

        //短信验证码
        //String val = this.stringRedisTemplate.opsForValue().get("KEY_PREFIX"+ user.getPhone());
        String cacheCode = "123";
        if ( !StringUtils.equals(code, cacheCode)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "验证码不对");
        }

        //生成salt
        String salt = "1";
        user.setSalt(salt);

        //密码加密
        //user.setPassword("1312");
        //参考博客: https://blog.csdn.net/qq_40794266/article/details/88655352
        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(CodecUtils.passwordBcryptEncode(user.getPassword(), salt));
        //校验密码调用相反的解密即可

        //强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());

        //写入数据库
        int b;
        try {
            logger.info("打印即将插入的user对象: "+ user);
            b = this.iUser.register(user);
            logger.info("打印写入成功后, 返回的b值: "+ b);  //正常是1
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "写入数据错误");
        }

        //删除redis
        if (b == 1){
            logger.info("删除了redis记录的短信校验码");
            //this.stringRedisTemplate.delete(KEY_PREFIX+ user.getPhone());
        }

        return ResponseResult.okResult("ok");
    }

    /**
     * 校验数据
     */
    public ResponseResult checkData(String data, Integer type){
        User user = new User();
        logger.info("user:开始对用户信息进行校验");

        //校验类型
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //查询
        User toBe_notToBe = null;
        try {
            logger.info("打印recode: "+ user);
            toBe_notToBe = this.iUser.checkUser(user);
        }
        catch (Exception e){
            System.out.println("");
        }

        if (toBe_notToBe == null) {
            return ResponseResult.okResult(0, "查询, 没有当前用户");
        } else {
            return ResponseResult.okResult(1, "查询, 存在用户");
        }

    }

    /**
     * 用户登录
     */
    public User userLogin(String username, String pwd){
        logger.info("user-service: 进入到user模块, 用户登录模块");

        //查询用户是否存在
        User findUser = null;
        try {
            findUser = iUser.selectByUserName(username);
            logger.info("根据username查询用户是否存在, 打印findUser: "+ findUser);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }

        if (findUser == null){
            return null;
        }

        //校验, 数据的密码和传入的密码是否一致
        Boolean isUser = CodecUtils.passwordConfirm(pwd+ findUser.getSalt(), findUser.getPassword());

        if ( isUser == false ){
            logger.warn("警告, 密码核对失败, 返回错误信息");
            return null;
        }

        logger.info("密码校验成功了, 这里存在风险, 因为直接返回了用户的敏感信息");
        return findUser;

    }
}
