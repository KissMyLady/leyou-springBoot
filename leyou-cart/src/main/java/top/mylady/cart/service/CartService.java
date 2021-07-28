package top.mylady.cart.service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
            logger.info("打印购物查到的bool对象: "+ bool);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (bool == true){
            //存在
            String json = redis.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            logger.info("购物车存在数据, 打印解析的json值cart: "+ cart);
            cart.setNum(cart.getNum()+ num);

        }else{
            //不存在
            cart.setUserId(user.getId());
            Sku sku = this.goodsClient.queryBySkuId(skuId);
            logger.info("打印从goods-service服务器查询到的商品sku信息: "+ sku);
//            cart.setImage("");
//            cart.setPrice(111L);
//            cart.setTitle("嵌入式云电脑");
//            cart.setOwnSpec("123");
            String image = "";
            Long price = 9999L;
            String title = "购物车添加失败";
            String own = "";
            try {
                image = StringUtils.isBlank(sku.getImages()) ? "": StringUtils.split(sku.getImages(), ",")[0];
                price  = sku.getPrice();
                title = sku.getTitle();
                own = sku.getOwnSpec();
            }
            catch (Exception e){
                System.out.println("错误, 原因e: "+ e);
            }

            cart.setImage(image);
            cart.setPrice(price);
            cart.setTitle(title);
            cart.setOwnSpec(own);
            //写入
            redis.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
        }

        return ResponseResult.okResult("购物车添加成功");
    }


    /**
     * 查询购物车
     */
    public ResponseEntity<List<Cart>> queryCart(){
        logger.info("cart-server层, 开始查询购物车");
        //获取用户
        ///UserInfo userInfo = LoginInterceptor.getLoginUser();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(111L);

        //判断是否存在购物车
        String key = KEY_PREFIX+ userInfo.getId();
        if ( !this.redisTemplate.hasKey(key)){
            return ResponseEntity.notFound().build();
        }

        redis = this.redisTemplate.boundHashOps(key);
        List<Object> items = redis.values();

        //List<Cart> carts = this.cartService.queryCart();
        List<Cart> carts = new ArrayList<>();

        carts.add(new Cart(1L, 2L, "", 1));
        carts.add(new Cart(13L, 66L, "", 13));
        carts.add(new Cart(3L, 2L, "", 1));

        if (carts == null){
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改购物车信息
     */
    public ResponseResult updateCart(Cart cart){
        logger.info("cart-server层, 开始查询购物车, 打印传递过来的cart: "+ cart);
        //获取用户
        ///UserInfo userInfo = LoginInterceptor.getLoginUser();

        if (cart.getSkuId() == null || cart.getNum() <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(111L);

        if (userInfo.getId() == null){
            logger.error("线程获取用户错误, 或是用户未登录, 请查明原因");
            return ResponseResult.errorResult(404, "用户未登录");
        }

        //判断是否存在购物车
        String key = KEY_PREFIX+ userInfo.getId();
        logger.info("打印拼接的key: "+ key);

        if ( !this.redisTemplate.hasKey(key)){
            logger.warn("警告, 不存在当前购物车信息, 失败");
            return ResponseResult.errorResult(404, "'不存在当前购物车信息'");
        }

        String cartJsons = "";
        try {
            cartJsons = cart.getSkuId().toString();
        }
        catch (Exception e){
            System.out.println("转换错误, 原因e: "+ e);
        }

        logger.info("cartJsons: "+ cartJsons);  //12

        Cart cart1;
        redis = this.redisTemplate.boundHashOps(key);


        String cartJson = null;
        try {
            /*
             * TODO: Bug: 这里redis提取不到hash数据, 为null
             * */
            cartJson = redis.get(cartJsons).toString();
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        cart1 = JsonUtils.parse(cartJson, Cart.class);
        logger.info("cart1: "+ cart1);
        //更新数量
        cart1.setNum(cart.getNum());
        //写入
        redis.put(cart.getSkuId().toString(), JsonUtils.serialize(cart1));
        return ResponseResult.okResult(200, "购物车修改成功");
    }

    /**
     * 删除购物车
     */
    public ResponseResult deleteCart(String skuId){
        logger.info("cart-server层, 开始删除购物车");
        //获取用户
        ///UserInfo userInfo = LoginInterceptor.getLoginUser();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(111L);

        if (userInfo.getId() == null){
            logger.error("线程获取用户错误, 或是用户未登录, 请查明原因");
            return ResponseResult.errorResult(404, "用户未登录");
        }
        //判断是否存在购物车
        String key = KEY_PREFIX+ userInfo.getId();
        if ( !this.redisTemplate.hasKey(key)){
            logger.warn("警告, 不存在当前购物车信息, 失败");
            return ResponseResult.errorResult(404, "'不存在当前购物车信息'");
        }
        try {
            redis = this.redisTemplate.boundHashOps(key);
            redis.delete(skuId);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return ResponseResult.errorResult(500, "服务器删除购物车失败, 原因是"+ e);
        }
        return ResponseResult.okResult("删除购物成功");
    }
}
