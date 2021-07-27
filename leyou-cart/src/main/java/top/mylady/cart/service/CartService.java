package top.mylady.cart.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.mylady.cart.interceptor.LoginInterceptor;
import top.mylady.utils.pojos.auth.UserInfo;
import top.mylady.cart.client.GoodsClient;
//import top.mylady.cart.interceptor.LoginInterceptor;
import top.mylady.utils.pojos.cart.Cart;
//import top.mylady.service.pojo.Sku;
import top.mylady.utils.JsonUtils;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import top.mylady.utils.pojos.goods.Sku;


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

    //redis链接
    BoundHashOperations<String, Object, Object> redis;

    static final String KEY_PREFIX = "leyou:cart:uid:";

    /**
     * 购物车增加服务
     */
    public ResponseResult addCart(Cart cart){

        if (cart.getSkuId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //获取登录用户
        //UserInfo user = LoginInterceptor.getLoginUser();
        UserInfo user = new UserInfo();
        user.setId(111L);
        user.setUsername("张麻子");

        if (user.getId() == null){
            logger.error("线程获取用户错误, 请查明原因");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        String key = KEY_PREFIX+ user.getId();

        //获取hash操作对象
        try {
            redis = this.redisTemplate.boundHashOps(key);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(500, "服务器redis挂了, 大佬你可长点心吧");
        }

        this.redisTemplate.boundHashOps(key);

        Long skuId = 1L;
        Integer num = 1;
        Boolean bool = false;

        //查询是否存在
        try {
            skuId = cart.getSkuId();
            num = cart.getNum();
            bool = redis.hasKey(skuId.toString());
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (bool){
            //存在
            String json = redis.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            logger.info("购物车存在数据, 打印解析的json值cart: "+ cart);
            cart.setNum(cart.getNum()+ num);

        }else{
            //不存在
            cart.setUserId(user.getId());
            //Sku sku = this.goodsClient.queryBySkuId(skuId);

            cart.setImage("");
            cart.setPrice(111L);
            cart.setTitle("嵌入式云电脑");
            cart.setOwnSpec("123");

            //写入
            redis.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
        }

        return ResponseResult.okResult("购物车添加成功");
    }

}
