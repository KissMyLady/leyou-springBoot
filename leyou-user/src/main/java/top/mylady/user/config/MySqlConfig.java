package top.mylady.user.config;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;


@Data
@Configuration
@ConfigurationProperties(prefix="mysql.core")
@PropertySource(value="classpath:mysql-core-jdbc.properties")
@MapperScan(basePackages="top.mylady.user.mappers",
        sqlSessionFactoryRef="mysqlCoreSessionFactory")  //扫描接口创建代理对象
public class MySqlConfig {

    private static final Logger logger = LoggerFactory.getLogger(MySqlConfig.class);

    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcPassword;
    private String jdbcDriver;
    private String rootMapper;      //mapper文件在classpath下存放的根路径
    private String aliasesPackage;  //别名包

    @Bean("mysqlCoreDataSource")
    public DataSource mysqlCoreDataSource() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setDriverClassName(jdbcDriver);
        logger.debug(String.format("数据库dataSource加载完毕, 交给Spring管理, 现在的连接对象是: %s", dataSource.getJdbcUrl()));
        return dataSource;
    }

    /**
     * 整合mybatis  SqlSessionFactoryBean
     */
    @Bean("mysqlCoreSessionFactory")
    public SqlSessionFactoryBean mysqlCoreSessionFactory(@Qualifier("mysqlCoreDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setDataSource(dataSource);  //数据源
        factoryBean.setTypeAliasesPackage(this.getAliasesPackage());  //设置包别名
        //mapper文件存储的位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:mappers/*/*.xml");
        factoryBean.setMapperLocations(resources);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        logger.debug("MysqlCoreSessionFactory 加载完毕, 返回factoryBean对象, 交给Spring管理");
        return factoryBean;
    }
}
