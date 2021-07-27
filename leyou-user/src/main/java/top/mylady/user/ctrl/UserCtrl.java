package top.mylady.user.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.user.pojos.User;
import top.mylady.user.service.UserService;
import top.mylady.user.utils.ResponseResult;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserCtrl {

    private static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);

    @Autowired
    private UserService userService;

    /**
     * 校验用户登录数据
     */
    @RequestMapping("/check/{data}/{type}")
    public ResponseResult checkUserData(@PathVariable("data") String data,
                                        @PathVariable("type") Integer type){
        return userService.checkData(data, type);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseResult register(@Valid User user, @RequestParam("code") String code){
        return userService.registerService(user, code);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("username") String username,
                                @RequestParam("pwd") String pwd ){
        User user = userService.userLogin(username, pwd);
        logger.info("user模块, ctrl: 使用spring自带的ResponseEntity返回user对象, 打印查询到的user: "+ user);
        return ResponseEntity.ok(user);
    }

}
