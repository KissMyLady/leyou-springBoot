package top.mylady.service.pojo;
import lombok.Data;


/**
 * 商品分类
 */
@Data
public class Category {

    private Long id;
    private String name;
    private Integer parentId;  //父类ID
    private Boolean isParent;  //是否为父类
    private Integer sort;      //排序

}
