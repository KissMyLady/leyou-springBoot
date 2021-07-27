package top.mylady.service.repository;
import top.mylady.service.esPojo.Product;
import java.util.List;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * 基于Spring Data的CRUD
 * 自定义接口继承ElasticsearchRepository<类名,id类型>
 *
 *     extends ElasticsearchRepository<Product, Long>
 */

public interface ItemRepository  {

    /**
     * 不需要实现
     */
    List<Product> findByTitle(String title);

    /**
     * 范围查item, 按照价格区间来查询
     */
    List<Product> findByPriceBetween(Double left, Double right);

}
