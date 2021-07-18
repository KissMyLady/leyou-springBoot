package top.mylady.service.mappers.category;
import org.apache.ibatis.annotations.Param;
import top.mylady.service.pojo.BrandCategory;
import top.mylady.service.pojo.Category;
import java.util.List;


/**
 * 分类查询
 */
public interface CategoryMapper {

    List<Category> selectByParentId(Integer pid);

    List<Category> queryByBrandId(@Param("bid") Long bid);

}
