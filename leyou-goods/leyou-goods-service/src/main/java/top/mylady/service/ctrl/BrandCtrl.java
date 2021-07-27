package top.mylady.service.ctrl;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.service.pojo.Brand;
import top.mylady.service.server.BrandService;
import top.mylady.service.server.CategoryService;
import top.mylady.utils.dtos.PageResponseResult;
import top.mylady.utils.dtos.ResponseResult;
import java.util.List;


//品牌
@RestController
@RequestMapping("/brand")
public class BrandCtrl {

    private static final Logger logger = LoggerFactory.getLogger(BrandCtrl.class);

    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public PageResponseResult queryBrand(@RequestBody @RequestParam(value="key", required=false) String key,
                                         @RequestParam(value="page",   defaultValue="1")  Integer page,
                                         @RequestParam(value="rows",   defaultValue="5" ) Integer rows,
                                         @RequestParam(value="sortBy", defaultValue="id") String sortBy,
                                         @RequestParam(value="desc",   required=false)    Boolean desc
    ){
        PageResponseResult pageResponseResult = brandService.queryBrandByPage(key, page, rows, sortBy, desc);
        return pageResponseResult;
    }

    /**
     * 新增品牌
     */
    @RequestMapping("/addBrand")
    public ResponseResult addBrandAndCategory(
            @RequestBody Brand brand,
            @Param("cids") List<Long> cids){
        logger.info("打印前端传递过来的参数brand: "+ brand +" cids: "+ cids);
        return  brandService.addBrand(brand, cids);
    }

    @Autowired
    private CategoryService categoryService;

    /**
     * 通过品牌id查询商品分类
     */
    @GetMapping("/bid/{bid}")
    public ResponseResult queryByBrandId(@PathVariable("bid") Long bid){
        logger.debug("打印传递过来的bid: "+ bid);
        return categoryService.queryByBrandId(bid);
    }


}
