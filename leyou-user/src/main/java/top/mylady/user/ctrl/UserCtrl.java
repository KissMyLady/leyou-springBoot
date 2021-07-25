package top.mylady.user.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.user.service.UserService;
import top.mylady.utils.dtos.ResponseResult;


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
}
