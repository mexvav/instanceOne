package core;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Component
public class Settings {

    @Autowired
    private Environment env;

    /*Database property*/
    public static final String dbPropertyDriverClassName = "datasource.driver-class-name";
    public static final String dbPropertyUrl = "datasource.url";
    public static final String dbPropertyUsername = "datasource.username";
    public static final String dbPropertyPassword = "datasource.password";

    /*JPA/Hibernate property*/
    public static final String dbPropertyShowSql = "jpa.show-sql";
    public static final String dbPropertyDdlAuto = "jpa.hibernate.ddl-auto";
    public static final String dbPropertyDialect = "jpa.properties.hibernate.dialect";
    public static final String dbPropertyCurrentSessionContextClass = "jpa.properties.hibernate.current_session_context_class";


    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(dbPropertyDriverClassName));
        dataSource.setUrl(env.getProperty(dbPropertyUrl));
        dataSource.setUsername(env.getProperty(dbPropertyUsername));
        dataSource.setPassword(env.getProperty(dbPropertyPassword));

        return dataSource;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", env.getProperty(dbPropertyShowSql));
        properties.put("hibernate.dialect", env.getProperty(dbPropertyDialect));
        properties.put("current_session_context_class", env.getProperty(dbPropertyCurrentSessionContextClass));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty(dbPropertyDdlAuto));

        // Fix Postgres JPA Error:
        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        return properties;
    }
}
