package top.mylady.user;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes=UserApp.class)
@RunWith(SpringRunner.class)
public class redisTest {

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
}
