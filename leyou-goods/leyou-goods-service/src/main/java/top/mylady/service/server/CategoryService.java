package top.mylady.service.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.category.CategoryMapper;
import top.mylady.service.pojo.BrandCategory;
import top.mylady.service.pojo.Category;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;
import java.util.List;


@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

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
            logger.error("品牌id查询 -> 分类Id查询错误, 原因e: \r\n"+ e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (categoryList == null || categoryList.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(categoryList);
    }
}
