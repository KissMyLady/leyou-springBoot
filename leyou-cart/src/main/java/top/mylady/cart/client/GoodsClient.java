package top.mylady.cart.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import top.mylady.service.pojo.Sku;
//import top.mylady.user.pojos.User;
import top.mylady.utils.dtos.ResponseResult;


/**
 * Eureka连接
 */
@Component
@FeignClient(value="goods-service")
public interface GoodsClient {

    /*
    * 这里 implements 服务接口即可自动创建, 很可以那里没有写接口
    * */


    //@PostMapping("/user/login")
    //User findUserByName(@RequestParam("username")String username, @RequestParam("pwd")String pwd);

    @PostMapping("/goods/sku/{id}")
    public abstract ResponseResult queryBySkuId(@PathVariable long id);



}
