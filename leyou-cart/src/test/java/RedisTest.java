import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.cart.CartApp;
import top.mylady.utils.JsonUtils;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import top.mylady.utils.pojos.cart.Cart;

import java.util.List;


@SpringBootTest(classes= CartApp.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private BoundHashOperations<String, Object, Object> redisConn;

    @Test
    public void testConn(){
        try {
            // 存储数据
            this.redisTemplate.opsForValue().set("key1", "redis测试成功......");
            // 获取数据
            String val = this.redisTemplate.opsForValue().get("key1");
            System.out.println("打印val = " + val);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }
    }

    /**
     * 提取redis数据
     */
    @Test
    public void testGetRedis(){
        System.out.println("开始测试redis提取数据");
        try {
            String key = "leyou:cart:uid:111";
            redisConn = this.redisTemplate.boundHashOps(key);
            List<Object> items = redisConn.values();

            //item: {"userId":111,"skuId":1,"title":"嵌入式云电脑","image":"",
            // "price":111,"num":1,"ownSpec":"123"}
            items.forEach( item -> {
                System.out.println("item: "+ item);
            });

        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }
    }

    @Test
    public void testUpdata(){
        Cart cart = new Cart();
        cart.setUserId(111L);
        cart.setTitle("生死疲劳");
        cart.setSkuId(12L);

        String key = "leyou:cart:uid:111";
        BoundHashOperations<String, Object, Object> hashRedis = this.redisTemplate.boundHashOps(key);

        String cartJsons = cart.getSkuId().toString();
        logger.info("打印cart.getSkuId().toString()提取的字符串对象: "+ cartJsons);

        Object raw_cartJson = hashRedis.get(cartJsons);
        logger.info("cartJson: "+ raw_cartJson);

        if (raw_cartJson == null){
            logger.error("为null, 结束程序");
            return;
        }

        String cartJson = raw_cartJson.toString();

        logger.info("cartJson: "+ cartJson);  //null ?

        Cart cart1 = JsonUtils.parse(cartJson, Cart.class);

        logger.info("cart1: "+ cart1);
    }


}
