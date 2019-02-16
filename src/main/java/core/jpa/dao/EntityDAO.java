package core.jpa.dao;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.jpa.interfaces.HasEntityCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
@Transactional
public class EntityDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(Object object) {
        sessionFactory.getCurrentSession().persist(object);
    }

    @Transactional
    public <T extends HasEntityCode> T get(Class<T> entityClass, double id) {
        return getSession().get(entityClass, id);
    }

    @Transactional
    public List getAll(String code) {
        return getSession().createQuery("FROM ".concat(code)).list();
    }

    @Transactional
    public void dropTable(String code) {
        getSession().createSQLQuery(String.format(Constants.SQL_DROP, code)).executeUpdate();
    }

    @Transactional
    public void cleanTable(String code) {
        getSession().createQuery(String.format(Constants.HQL_CLEAN, code)).executeUpdate();
    }

    @Transactional
    public Set<String> getAllTables() {
        Set<String> tableNames = Sets.newHashSet();
        try {
            ResultSet result =
                    Objects.requireNonNull(getMetaData()).getTables(
                            null, null, null, new String[]{core.jpa.Constants.EntityDAO.TABLE});
            while (result.next()) {
                tableNames.add(result.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return tableNames;
    }

    @Transactional
    public Set<Map<String, String>> getColumns(String table) {
        Set<Map<String, String>> columns = Sets.newHashSet();
        try {
            ResultSet result = Objects.requireNonNull(getMetaData()).
                    getColumns(null, null, table.toLowerCase(), null);
            while (result.next()) {
                Map<String, String> values = Maps.newHashMap();
                ResultSetMetaData metaData = result.getMetaData();
                int count = result.getMetaData().getColumnCount();
                for (int i = 1; i < count; ++i) {
                    values.put(metaData.getColumnName(i), result.getString(i));
                }
                columns.add(values);
            }
            return columns;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Connection getConnection() throws SQLException {
        return sessionFactory
                .getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(ConnectionProvider.class)
                .getConnection();
    }

    private DatabaseMetaData getMetaData() throws SQLException {
        return Objects.requireNonNull(getConnection()).getMetaData();
    }
}