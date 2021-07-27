package top.mylady.service.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.mylady.service.pojo.Sku;
import top.mylady.utils.dtos.ResponseResult;
import org.springframework.http.ResponseEntity;


@Service
public class GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);


    /**
     * 根据id查询sku信息
     */
    public ResponseEntity querySkuById(long id){
        logger.info("goods模块, 服务器层, 根据sku查询商品, 打印传递过来的id值: "+ id);
        Sku sku = new Sku();

        if (sku != null){
            return ResponseEntity.ok(sku);
        }else{
            return ResponseEntity.notFound().build();
        }

    }


}
