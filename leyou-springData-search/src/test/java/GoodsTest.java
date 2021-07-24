import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.EsApp;
import top.mylady.search.pojos.Goods;
import top.mylady.search.repository.GoodsRepository;


/**
 * Es搜索, 商品测试类
 */
@SpringBootTest(classes= EsApp.class)
@RunWith(SpringRunner.class)
public class GoodsTest {

    private static final Logger logger = LoggerFactory.getLogger(GoodsTest.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //注入jpa查询
    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 查询Mysql数据, 然后插入到Es中
     */
    @Test
    public void testInsertToElasticsearch(){
        logger.info("查询Mysql数据, 然后插入到Es中");


    }

    /**
     * 创建一个Product映射
     */
    @Test
    public void testConn(){
        try {
            //7.13.2版本 直接就创建了, 不需要再create, 否则报错
            IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Goods.class);
            //创建索引库
            //indexOperations.create();
            //Creates the index mapping for the entity this IndexOperations is bound to.
            //为该IndexOperations绑定到的实体创建索引映射。 有一个为给定类创建索引的重载, 需要类的字节码文件
            Document mapping = indexOperations.createMapping();
            //writes a mapping to the index  将刚刚通过类创建的映射写入索引
            indexOperations.putMapping(mapping);  //通过indexOperations还能判断索引库是否存在,删除索引库等等

            logger.info("创建Goods索引成功 !");
        }
        catch (Exception e){
            System.out.println("创建错误, 原因e: "+ e);
        }
    }
}
