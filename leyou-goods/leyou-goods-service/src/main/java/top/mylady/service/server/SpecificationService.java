package top.mylady.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.specification.SpecGroupMapper;
import top.mylady.service.pojo.Sku_SpecGroup;
import top.mylady.service.pojo.Sku_SpecParam;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;

import java.util.List;


@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specificationMapper;

    public ResponseResult queryGroupsByCid(Long cid) {
        Sku_SpecParam specParam = new Sku_SpecParam();
        specParam.setCid(cid);

        List<Sku_SpecGroup> specGroupList;
        //查询
        try {
            specGroupList = this.specificationMapper.queryGroupsByCid(cid);
        } catch (Exception e) {
            System.out.println("queryGroupsByCid查询错误, 原因e: " + e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        return ResponseResult.okResult(specGroupList);
    }

    /**
     * 查询tb_spec_param
     */
    public ResponseResult queryGroupByGid(Long gid) {

        //校验
        if (gid < 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //Sku_SpecParam specParam = new Sku_SpecParam();
        //specParam.setGroupId(gid);
        List<Sku_SpecParam> specParams;
        try {
            specParams = specificationMapper.queryByGid(gid);
        } catch (Exception e) {
            System.out.println("错误, 原因e: " + e);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(specParams);
    }

}
