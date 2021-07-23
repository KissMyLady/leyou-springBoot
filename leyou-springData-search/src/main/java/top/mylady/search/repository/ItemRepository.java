package top.mylady.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.mylady.search.pojos.Product;

import java.util.List;


/**
 * 基于Spring Data的CRUD
 * 自定义接口继承ElasticsearchRepository<类名,id类型>
 */
public interface ItemRepository extends ElasticsearchRepository<Product, Long> {

    /**
     * 不需要实现
     */
    List<Product> findByTitle(String title);

    /**
     * 范围查item, 按照价格区间来查询
     */
    List<Product> findByPriceBetween(Double left, Double right);

}
