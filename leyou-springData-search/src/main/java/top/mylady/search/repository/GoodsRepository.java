package top.mylady.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.mylady.search.pojos.Goods;


public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

}
