package top.mylady.service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient  //开启Eureka客户端发现
public class GoodsServerApp {

    public static void main(String[] args) {

        SpringApplication.run(GoodsServerApp.class, args);
    }
}
