package top.mylady.service.pojo;

import lombok.Data;


@Data
public class Sku_SpecParam {

    private Long id;
    private Long cid;
    private Long groupId;
    private String name;

    private Boolean numeric;
    private String unit;
    private Boolean generic;

    private Boolean searching;
    private String segments;
}
