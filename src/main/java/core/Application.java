package core;

import core.jpa.session_factory.ReloadableSessionFactory;
import core.jpa.session_factory.ReloadableSessionFactoryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        })
public class Application {

    @Autowired
    Settings settings;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySetting(
                org.hibernate.cfg.Environment.DATASOURCE, settings.getDataSource());
        serviceRegistryBuilder.applySettings(settings.getHibernateProperties());
        StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        return new ReloadableSessionFactoryBuilder(
                settings.getHibernateProperties(), serviceRegistry, "core")
                .build();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            return ((ReloadableSessionFactory) sessionFactory).getPlatformTransactionManager();
        }
        return new HibernateTransactionManager(sessionFactory);
    }
}
