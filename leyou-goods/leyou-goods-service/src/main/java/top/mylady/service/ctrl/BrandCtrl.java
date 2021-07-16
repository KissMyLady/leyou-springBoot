package top.mylady.service.ctrl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.service.pojo.Brand;
import top.mylady.service.server.BrandService;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;

import java.util.List;

/**
 * 品牌
 */
@RestController
@RequestMapping("/brand")
public class BrandCtrl {

    private static final Logger logger = LoggerFactory.getLogger(BrandCtrl.class);

    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseResult queryBrand(@RequestBody
            @RequestParam(value="key",    required=false)    String key,
            @RequestParam(value="page",   defaultValue="1")  Integer page,
            @RequestParam(value="rows",   defaultValue="5" ) Integer rows,
            @RequestParam(value="sortBy", defaultValue="id") String sortBy,
            @RequestParam(value="desc",   required=false)    Boolean desc
    ){
        List<Brand> brandList = brandService.queryBrandByPage(key, page, rows, sortBy, desc);
        if (brandList.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(brandList);
    }


}
