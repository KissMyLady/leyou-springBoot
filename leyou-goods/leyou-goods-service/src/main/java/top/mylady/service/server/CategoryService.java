package top.mylady.service.server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.category.CategoryMapper;
import top.mylady.service.pojo.Category;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;

import java.util.List;


/**
 * 省略了接口编写, 直接写了实现类
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父id查询类别
     */
    public List<Category> queryCategoryById(Integer pid){
        return categoryMapper.selectByParentId(pid);
    }

    /**
     * 品牌id查询 -> 分类Id
     */
    public ResponseResult queryByBrandId(Long bid){
        List<Category> categoryList;
        try {
            categoryList = this.categoryMapper.queryByBrandId(bid);
        }
        catch (Exception e){
            System.out.println("程序错误, 原因e: "+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(categoryList);
    }
}
