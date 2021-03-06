package top.mylady.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableZuulProxy        //开启Zuul网关
@EnableDiscoveryClient  //开启Eureka客户端发现
public class GatewayApp {

    public static void main(String[] args) {

        SpringApplication.run(GatewayApp.class, args);
    }
}
