package top.mylady.service.server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.category.CategoryMapper;
import top.mylady.service.pojo.Category;

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

}
