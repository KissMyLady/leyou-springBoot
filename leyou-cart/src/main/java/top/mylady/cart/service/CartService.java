package top.mylady.cart.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.mylady.cart.pojo.UserInfo;
import top.mylady.cart.client.GoodsClient;
//import top.mylady.cart.interceptor.LoginInterceptor;
import top.mylady.cart.pojo.Cart;
//import top.mylady.service.pojo.Sku;
import top.mylady.utils.JsonUtils;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;


/**
 * 购物车服务层
 */
@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "leyou:cart:uid:";


    public ResponseResult addCart(Cart cart){

        //获取登录用户
        //UserInfo user = LoginInterceptor.getLoginUser();
        UserInfo user = new UserInfo();

        if (user.getId() == null){
            logger.error("线程获取用户错误, 请查明原因");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        String key = KEY_PREFIX+ user.getId();

        //获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        //查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean bool = hashOps.hasKey(skuId.toString());

        if (bool){
            //存在
            String json = hashOps.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            logger.info("购物车存在数据, 打印解析的json值cart: "+ cart);
            cart.setNum(cart.getNum()+ num);
        }else{
            //不存在

            cart.setUserId(user.getId());
            ResponseResult sku = this.goodsClient.queryBySkuId(skuId);
        }

        return ResponseResult.okResult("添加成功");
    }

}
