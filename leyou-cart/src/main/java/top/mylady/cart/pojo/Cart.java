package top.mylady.cart.pojo;
import lombok.Data;


/**
 * 购物车
 */
@Data
public class Cart {

    //用户Id
    private Long userId;

    //商品id
    private Long skuId;

    //标题
    private String title;

    private String image;

    private Long price;

    //购买数量
    private Integer num;

    //商品规格参数
    private String ownSpec;
}
