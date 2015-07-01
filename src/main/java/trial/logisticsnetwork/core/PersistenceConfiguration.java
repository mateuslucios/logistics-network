package trial.logisticsnetwork.core;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


@Configuration
public class PersistenceConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws PropertyVetoException {

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/logistics_network");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("");
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setMaxActive(100);
        p.setInitialSize(10);

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(p);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPersistenceUnitName("logistics-network-pu");
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws PropertyVetoException {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}

