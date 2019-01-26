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

        dataSource.setDriverClassName(getProperty(Constants.dbPropertyDriverClassName));
        dataSource.setUrl(getProperty(Constants.dbPropertyUrl));
        dataSource.setUsername(getProperty(Constants.dbPropertyUsername));
        dataSource.setPassword(getProperty(Constants.dbPropertyPassword));

        return dataSource;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(Constants.hibernatePropShowSql, getProperty(Constants.dbPropertyShowSql));
        properties.put(Constants.hibernatePropDialect, getProperty(Constants.dbPropertyDialect));
        properties.put(Constants.hibernatePropCurSCtx, getProperty(Constants.dbPropertyCurSCtx));
        properties.put(Constants.hibernatePropHbm2ddl, getProperty(Constants.dbPropertyDdlAuto));
        properties.put(Constants.hibernatePropMetaDef, getProperty(Constants.dbPropertyMetaDef));

        return properties;
    }

    public String getProperty(String key) {
        return env.getProperty(key);
    }
}
