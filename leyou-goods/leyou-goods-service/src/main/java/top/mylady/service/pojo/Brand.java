package top.mylady.service.pojo;
import lombok.Data;


/**
 * 品牌分类
 */
@Data
public class Brand {

    private Long id;
    private String name;
    private String image;
    private Character letter;  //首字母

}
