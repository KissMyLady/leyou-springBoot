package top.mylady.search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import top.mylady.search.pojos.Product;


@SpringBootApplication
public class EsApp {

    public static void main(String[] args) {
        System.out.println("Hello World");
        SpringApplication.run(EsApp.class, args);

    }
}
