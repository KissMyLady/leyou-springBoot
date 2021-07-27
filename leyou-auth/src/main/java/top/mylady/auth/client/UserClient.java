package top.mylady.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.user.pojos.User;


/**
 * 连接到Eureka注册中心, 连接到user模块服务
 */
@Component
@FeignClient(value="user-service")
public interface UserClient {


    @PostMapping("/user/login")
    User findUserByName(@RequestParam("username")String username,
                        @RequestParam("pwd")String pwd);


}
