package top.mylady.service.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.brand.BrandMapper;
import top.mylady.service.pojo.Brand;
import java.util.ArrayList;
import java.util.List;


/**
 * 查询服务
 */
@Service
public class BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    private BrandMapper brandMapper;


    /**
     * key:    搜索关键词
     * page:   当前页
     * rows:   每页大小
     * sortBy: 排序字段
     * desc:   是否降序
     * 传递值如下: key=, page=1, rows=5, sortBy=id, desc=false
     */
    public List<Brand> queryBrandByPage(String key,
                                        Integer page,
                                        Integer rows,
                                        String sortBy,
                                        Boolean desc){

        //校验
        if (page < 0 || rows <= 0){
            page = 0;
            rows = 5;
        }

        //简单的校验字符串
//        if (key.contains(";") || key.contains("where")){
//            key = null;
//        }

        //查询总条数
        Integer countNums = brandMapper.queryBrandCount();

        //计算分页
        int raw_pages = countNums / rows;

        //计算总页数, 是否整除
        int pages;
        if ( countNums * rows == raw_pages){
            pages = raw_pages;
        }else {
            pages = raw_pages +1;
        }

        //分页起始数据计算, 默认从第一页开始
        int startNum = 0;
        if (page > 1){
            //起始数据 = 分页 * 分页数
            startNum = page * rows;
        }

        List<Brand> brandList;
        try {
            brandList = brandMapper.queryBrandByPage(key, startNum, rows, sortBy, desc);
        }
        catch (Exception e){
            brandList = new ArrayList<>();
        }

        return brandList;
    }

}