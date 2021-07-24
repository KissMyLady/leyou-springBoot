import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.EsApp;
import top.mylady.search.repository.ItemRepository;
import top.mylady.search.pojos.Product;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= EsApp.class)
public class SpringDataEsTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataEsTest.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //注入jpa查询
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testSaveAll2() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1L,"OPPOFindX2","手机","OPPO",4999d,"http://image.leyou.com/13123.jpg"));
        list.add(new Product(2L,"OPPOFindX","手机","OPPO",3999d,"http://image.leyou.com/13123.jpg"));
        list.add(new Product(3L,"OPPORENO","手机","OPPO",2999d,"http://image.leyou.com/13123.jpg"));
        list.add(new Product(4L,"小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Product(5L,"坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Product(6L,"华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Product(7L,"小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Product(8L,"荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        itemRepository.saveAll(list);  //通过传入对象列表的方式保存多个文档
        Iterable<Product> all = itemRepository.findAll();  //查询索引库中所有文档 还有部分find,delete相关方法就不演示了
        all.forEach(System.out::println);
    }

    /**
     * 创建一个Product映射
     */
    @Test
    public void testConnEs(){
        logger.info("开始创建mapping");
        try {
            //7.13.2版本 直接就创建了, 不需要再create, 否则报错
            IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Product.class);
            //创建索引库
            //indexOperations.create();
            //Creates the index mapping for the entity this IndexOperations is bound to.
            //为该IndexOperations绑定到的实体创建索引映射。 有一个为给定类创建索引的重载, 需要类的字节码文件
            Document mapping = indexOperations.createMapping();
            //writes a mapping to the index  将刚刚通过类创建的映射写入索引
            indexOperations.putMapping(mapping);  //通过indexOperations还能判断索引库是否存在,删除索引库等等

        }
        catch (Exception e){
            System.out.println("创建错误, 原因e: "+ e);
        }
        logger.info("创建mapping成功......");
    }

    /**
     * 删除映射中的一条数据
     */
    @Test
    public void testDelete(){
        try {
            elasticsearchRestTemplate.delete(
                    new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("title", "OPPOFindX")).build(),
                    Product.class,
                    IndexCoordinates.of("product")
                    // 打印还存在的数据, 没必要了
                    // SearchHits<Product> search = elasticsearchRestTemplate.search(
                    //         new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).build(),
                    //         Product.class);
                    // search.forEach(System.out::println);
            );
        }
        catch (Exception e){
            System.out.println("删除错误, 原因e: "+ e);
        }
    }

    /**
     * 查询全部, 并按照价格排序
     */
    @Test
    public void testRepositoryQuery(){
        Iterable<Product> products = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));

        products.forEach( item -> {
            System.out.println("item: "+ item);
        });
    }

    /**
     * 自定义的一个接口查询, 查询价格区间
     */
    @Test
    public void queryBetween(){
        List<Product> productList = this.itemRepository.findByPriceBetween(2000.00, 3500.00);
        productList.forEach( item -> {
            System.out.println("item: "+ item);
        });
    }

    /**
     * 高级普通查询
     */
    @Test
    public void searchMethod_1(){
        //利用构造器建造NativeSearchQuery  他可以添加条件,过滤,等复杂操作
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("title", "OPPOFindX")).build();

        //elasticsearchRestTemplate.search方法参数一,本机查询的构造,参数二index的类,可选参数三再次声明库名(可以多个)
        SearchHits<Product> search = elasticsearchRestTemplate.search(query, Product.class);
        search.forEach(
                searchHit -> {
                    System.out.println(searchHit.getContent());
                }
        );
    }

    /**
     * 排序分页查询
     */
    @Test
    public void queryPageSort(){
        try {
            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("category", "手机"))
                    //添加分页  注意页码是从0开始的
                    //pageable的实现类PageRequest的静态方法of
                    //要排序就增加参数3 Sort.Direction.ASC升  Sort.Direction.DESC降
                    .withPageable(PageRequest.of(1,4))
                    //排序整体
                    //根据字段排序fieldSort("字段名")   .order(SortOrder.ASC/DESC)
                    .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
                    .build();

            //elasticsearchRestTemplate.search方法参数一,本机查询的构造,参数二index的类,可选参数三再次声明库名(可以多个)
            SearchHits<Product> search = elasticsearchRestTemplate.search(query, Product.class);

            //打印查询到的数据
            search.forEach(
                    searchHit-> {
                        System.out.println(searchHit.getContent());
                    }
            );
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }
    }

    /**
     * 上面的另外一种写法
     */
    @Test
    public void testNativeQuery() {
        System.out.println("开始遍历了>>>");
        for (int i = 1; i < 5; i++) {
            int size = 3;
            this.SplitPage(i, size);
        }
    }

    public void SplitPage(int page, int size){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        //int page = 0;
        //int size = 3;
        //设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        //执行
        SearchHits<Product> search = this.elasticsearchRestTemplate.search(queryBuilder.build(), Product.class);
        search.forEach( item -> {
            System.out.println("item: "+ item);
        });
    }


    /**
     * 聚合查询
     */
    @Test
    public void AggregationsQuery(){
        try {
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

            //聚合可以有多个,所以add
            //terms词条聚合,传入聚合名称   field("聚合字段")   size(结果集大小)
            NativeSearchQuery query = nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"))
                    //结果集过滤  这里设置不需要结果集(不添加包含与不包含,会自动生成length为0数组)
                    .withSourceFilter(new FetchSourceFilterBuilder().build())
                    .build();

            SearchHits<Product> hits = elasticsearchRestTemplate.search(query, Product.class);
            System.out.println("hits: "+ hits);

            //获取聚合结果集   因为结果为字符串类型 所以用ParsedStringTerms接收   还有ParsedLongTerms接收数字  ParsedDoubleTerms接收小数
            Aggregations aggregations = hits.getAggregations();
            assert aggregations != null;
            ParsedStringTerms brands = aggregations.get("brands");

            //获取桶
            brands.getBuckets().forEach(bucket->{
                //获取桶中的key 与 记录数
                System.out.println(bucket.getKeyAsString()+" "+bucket.getDocCount());
            });
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }

    }


    /**
     * 嵌套聚合查询
     */
    @Test
    public void AggNestingQuery(){
        try {
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            //聚合可以有多个,所以add
            //terms词条聚合,传入聚合名称   field("聚合字段")
            NativeSearchQuery query = nativeSearchQueryBuilder
                    .addAggregation(
                            AggregationBuilders
                                    .terms("brands")
                                    .field("brand")
                                    //添加子聚合  subAggregation(添加方式是一样的)  值为桶中品牌均价
                                    .subAggregation(AggregationBuilders.avg("price_avg").field("price"))
                    )
                    //结果集过滤  这里设置不需要结果集(不添加包含与不包含,会自动生成长为0数组)
                    .withSourceFilter(new FetchSourceFilterBuilder().build())
                    .build();

            SearchHits<Product> hits = elasticsearchRestTemplate.search(query, Product.class);
            System.out.println(hits);

            //获取聚合结果集   因为结果为字符串类型 所以用ParsedStringTerms接收   还有ParsedLongTerms接收数字  ParsedDoubleTerms接收小数
            Aggregations aggregations = hits.getAggregations();
            assert aggregations != null;
            ParsedStringTerms brands = aggregations.get("brands");

            //获取桶brands
            brands.getBuckets().forEach(bucket->{
                //获取桶中的key 与 记录数
                System.out.println(bucket.getKeyAsString()+" "+bucket.getDocCount());

                //获取嵌套的桶price_avg
                ParsedAvg price_avg = bucket.getAggregations().get("price_avg");
                System.out.println(price_avg.getValue());
            });
        }

        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }
    }

}
