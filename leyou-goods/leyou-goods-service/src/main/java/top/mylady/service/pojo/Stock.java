package top.mylady.service.pojo;
import lombok.Data;


@Data
public class Stock {

    private Long skuId;

    //秒杀可用库存
    private Integer seckillStock;

    //已秒杀数量
    private Integer seckillTotal;

    //正常库存
    private Long stock;
}
