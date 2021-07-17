package top.mylady.service.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.service.server.BrandService;
import top.mylady.utils.dtos.PageResponseResult;


/**
 * 品牌
 */
@RestController
@RequestMapping("/goods/brand")
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


}
