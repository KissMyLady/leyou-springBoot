package top.mylady.service.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.service.GoodsServerApp;
import top.mylady.service.pojo.Sku;
import top.mylady.service.server.GoodsService;
import top.mylady.utils.dtos.ResponseResult;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/goods")
public class GoodsCtrl {

    private static final Logger logger = LoggerFactory.getLogger(GoodsCtrl.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 根据id查询sku
     */
    @GetMapping("/sku/{id}")
    public ResponseEntity querySkuById(@PathVariable("id") Long id){
        return goodsService.querySkuById(id);
    }
}
