package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class Application {

    @Value("${VCAP_SERVICES}")
    private String vcapJsonString;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean actionServletRegistration(ActionServlet actionServlet) {
        return new ServletRegistrationBean(actionServlet, "/moviefun/*");
    }

    @Bean
    public DatabaseServiceCredentials databaseServiceCredentials() {
        return new DatabaseServiceCredentials(this.vcapJsonString);
    }

//    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql"));
//        return dataSource;
//    }
//
//
//    public DataSource movieDataSource(DatabaseServiceCredentials serviceCredentials) {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setURL(serviceCredentials.jdbcUrl("movies-mysql"));
//        return dataSource;
//    }

//    @Bean
//    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase(albumsDataSource(()));
//        return adapter;
//    }
//
//    @Bean(name = "albumEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean albumEntityManagerFactoryBean() {
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(albumsDataSource(databaseServiceCredentials()));
//        factoryBean.setPackagesToScan("org.superbiz.moviefun");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setShowSql(true);
////        vendorAdapter.setDatabasePlatform(hibernateDialect);
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.show_sql","create");
//        jpaProperties.put("hibernate.hbm2ddl.auto",true);
//        factoryBean.setJpaProperties(jpaProperties);
//
//        return factoryBean;
//
//    }
//
//    @Primary
//    @Bean(name = "movieEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean movieEntityManagerFactoryBean() {
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(movieDataSource(databaseServiceCredentials()));
//        factoryBean.setPackagesToScan("org.superbiz.moviefun");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setShowSql(true);
////        vendorAdapter.setDatabasePlatform(hibernateDialect);
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.show_sql",true);
//        jpaProperties.put("hibernate.hbm2ddl.auto","create");
//        factoryBean.setJpaProperties(jpaProperties);
//
//        return factoryBean;
//
//    }
}
