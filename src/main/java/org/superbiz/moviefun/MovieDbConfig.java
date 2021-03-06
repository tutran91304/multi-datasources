package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MovieDbConfig {

    @Bean
    public DataSource movieDataSource(DatabaseServiceCredentials databaseServiceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(databaseServiceCredentials.jdbcUrl("movies-mysql"));

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean moviesEntityManagerFactory(DataSource movieDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(movieDataSource);
        factoryBean.setPackagesToScan("org.superbiz.moviefun");
        factoryBean.setPersistenceUnitName("movies");

        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql",true);
        jpaProperties.put("hibernate.hbm2ddl.auto","create");
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager moviesPlatformTransactionManager(EntityManagerFactory moviesEntityManagerFactory) {
        return new JpaTransactionManager(moviesEntityManagerFactory);
    }
}
