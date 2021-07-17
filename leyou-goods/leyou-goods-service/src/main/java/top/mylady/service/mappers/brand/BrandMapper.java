package top.mylady.service.mappers.brand;
import org.apache.ibatis.annotations.Param;
import top.mylady.service.pojo.Brand;
import java.util.List;


public interface BrandMapper {

    List<Brand> queryBrand();

    List<Brand> queryBrandByPage(
            @Param("key") String key,
            @Param("page") Integer startNum,
            @Param("rows") Integer rows,
            @Param("sortBy") String sortBy,
            @Param("desc") Boolean desc
    );

    /**
     * 查询总条数
     */
    Integer queryBrandCount();

    /**
     * 新增品牌, 插入品牌与分类的关系
     */
    void insertBrandAndCategory(
            @Param("cid") Long cid,
            @Param("bid") Long bid
    );
}
