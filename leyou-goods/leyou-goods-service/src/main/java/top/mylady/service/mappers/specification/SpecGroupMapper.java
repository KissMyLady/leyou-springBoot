package top.mylady.service.mappers.specification;

import org.apache.ibatis.annotations.Param;
import top.mylady.service.pojo.Sku_SpecGroup;
import top.mylady.service.pojo.Sku_SpecParam;

import java.util.List;


public interface SpecGroupMapper {

    List<Sku_SpecGroup> queryGroupsByCid(@Param("cid") Long cid);

    List<Sku_SpecParam> queryByGid(@Param("gid") Long gid);
}
