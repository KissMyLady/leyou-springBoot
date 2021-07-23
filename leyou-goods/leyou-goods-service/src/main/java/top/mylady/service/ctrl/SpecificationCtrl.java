package top.mylady.service.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.service.server.SpecificationService;
import top.mylady.utils.dtos.ResponseResult;


//规格
@RestController
@RequestMapping("/spec")
public class SpecificationCtrl {

    @Autowired
    private SpecificationService specificationService;


    @RequestMapping("/groups/{cid}")
    public ResponseResult queryGroupsByCid(@PathVariable("cid") Long cid) {
        return specificationService.queryGroupsByCid(cid);
    }

    @RequestMapping("/params")
    public ResponseResult queryGroupByGid(@RequestParam("gid") Long gid) {
        return specificationService.queryGroupByGid(gid);
    }

}
