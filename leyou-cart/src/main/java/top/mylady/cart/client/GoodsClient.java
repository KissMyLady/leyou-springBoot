package top.mylady.cart.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
//import top.mylady.service.pojo.Sku;
//import top.mylady.user.pojos.User;
import top.mylady.utils.dtos.ResponseResult;
import top.mylady.utils.pojos.goods.Sku;


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

    @RequestMapping("/goods/sku/{id}")
    public abstract Sku queryBySkuId(@PathVariable Long id);



}
