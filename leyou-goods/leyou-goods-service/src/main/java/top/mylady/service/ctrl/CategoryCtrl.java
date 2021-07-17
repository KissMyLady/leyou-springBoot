package top.mylady.service.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.service.pojo.Category;
import top.mylady.service.server.CategoryService;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import java.util.List;


@RestController  //@RestController注解相当于@ResponseBody+ @Controller合在一起
@RequestMapping("/goods/category")
public class CategoryCtrl {

    private static final Logger logger = LoggerFactory.getLogger(CategoryCtrl.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResponseResult<List<Category>> queryCategoryById(@RequestParam("pid") Integer pid){

        if (pid == null || pid < 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        List<Category> categoryList = categoryService.queryCategoryById(pid);

        if (categoryList.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        logger.debug("CategoryCtrl: 成功查询到分类数据, 返回结果");
        return ResponseResult.okResult(categoryList);
    }

}
