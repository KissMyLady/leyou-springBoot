package top.mylady.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class CartApp {

    public static void main(String[] args) {
        System.out.println("cart-service 启动");
        SpringApplication.run(CartApp.class, args);
        System.out.println("cart-service Spring服务启动成功");
    }
}
