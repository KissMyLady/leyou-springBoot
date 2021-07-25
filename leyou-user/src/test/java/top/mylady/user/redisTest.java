package top.mylady.user;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes=UserApp.class)
@RunWith(SpringRunner.class)
public class redisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

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
}
