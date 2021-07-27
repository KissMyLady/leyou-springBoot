package top.mylady.utils.pojos.goods;
import lombok.Data;
import java.util.Date;


@Data
public class Spu {

    private Long id;
    private Long brandId;

    //1级类目
    private Long cid1;

    //2级类目
    private Long cid2;

    //3级类目
    private Long cid3;

    //标题
    private String title;

    //子标题
    private String subTitle;

    //是否上架
    private Boolean saleable;

    //是否有效,逻辑删除使用
    private Boolean valid;

    //创建时间
    private Date createTime;

    //最后修改时间
    private Date lastUpdateTime;
}
