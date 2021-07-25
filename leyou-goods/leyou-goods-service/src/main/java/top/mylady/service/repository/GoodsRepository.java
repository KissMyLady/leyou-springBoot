package top.mylady.service.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.mylady.service.esPojo.Goods;


public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

}
