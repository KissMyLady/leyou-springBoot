package top.mylady.service.pojo;

import lombok.Data;

import java.util.List;


@Data
public class Sku_SpecGroup {

    private Long id;
    private Long cid;
    private String name;
    private List params;
}
