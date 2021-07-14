package top.mylady.service.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.service.pojo.User;
import top.mylady.service.server.UserService;


@RestController
@RequestMapping("/test")
public class TestCtel {

    private static final Logger logger = LoggerFactory.getLogger(TestCtel.class);

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public User queryById(@RequestParam(value="id", required=true) Long id){
        if (id == null){
            User user = new User();
            user.setUsername("警告, id值为空了");
            return user;
        }
        logger.debug("路由转发层, 开始调用服务层查询用户并返回, 打印传递过来的id值: "+ id);
        return this.userService.queryById(id);
    }

    @GetMapping("/hi")
    public String Hi(){
        return "Hi";
    }
}
