package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Component
public class Settings {

    @Autowired
    private Environment env;

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(getProperty(Constants.JPA.DB_PROPERTY_DRIVER_CLASS_NAME));
        dataSource.setUrl(getProperty(Constants.JPA.DB_PROPERTY_URL));
        dataSource.setUsername(getProperty(Constants.JPA.DB_PROPERTY_USERNAME));
        dataSource.setPassword(getProperty(Constants.JPA.DB_PROPERTY_PASSWORD));

        return dataSource;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(
                Constants.JPA.HIBERNATE_PROP_SHOW_SQL, getProperty(Constants.JPA.DB_PROPERTY_SHOW_SQL));
        properties.put(
                Constants.JPA.HIBERNATE_PROP_DIALECT, getProperty(Constants.JPA.DB_PROPERTY_DIALECT));
        properties.put(
                Constants.JPA.HIBERNATE_PROP_CUR_S_CTX, getProperty(Constants.JPA.DB_PROPERTY_CUR_S_CTX));
        properties.put(
                Constants.JPA.HIBERNATE_PROP_HBM_2_DDL, getProperty(Constants.JPA.DB_PROPERTY_DDL_AUTO));
        properties.put(
                Constants.JPA.HIBERNATE_PROP_META_DEF, getProperty(Constants.JPA.DB_PROPERTY_META_DEF));

        return properties;
    }

    public String getProperty(String key) {
        return env.getProperty(key);
    }
}
