package top.mylady.utils.pojos.goods;
import lombok.Data;


@Data
public class SpuDetail {

    private Long spuId;

    //商品描述
    private String description;

    //商品特殊规格的名称及可选值模板
    private String specTemplate;

    //商品的全局规格属性
    private String specifications;

    //包装清单
    private String packingList;

    //售后服务
    private String afterService;
}
