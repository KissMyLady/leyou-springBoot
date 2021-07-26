package top.mylady.user.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.user.pojos.User;
import top.mylady.user.service.UserService;
import top.mylady.utils.dtos.ResponseResult;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserCtrl {

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

}
