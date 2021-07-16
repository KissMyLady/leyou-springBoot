package top.mylady.service.mappers.category;
import top.mylady.service.pojo.Category;
import java.util.List;


/**
 * 分类查询
 */
public interface CategoryMapper {

    List<Category> selectByParentId(Integer pid);

}
