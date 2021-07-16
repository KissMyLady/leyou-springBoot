package top.mylady.utils.dtos;
import lombok.Data;

import java.util.List;


@Data
public class PageRequestDto<T> {

    protected Integer total;      // 总条数
    protected Integer totalPage;  // 总页数
    protected List<T> items;      // 当前页数

    protected Integer page;   //当前页

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.totalPage == null || this.totalPage < 0 || this.totalPage > 100) {
            setTotal(10);
        }
    }
}
