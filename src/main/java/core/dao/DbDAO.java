package core.dao;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
@Transactional
public class DbDAO extends AbstractDAO {

    @Autowired
    protected DbDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Drop database from database
     *
     * @param table the table name
     */
    @Transactional
    public void dropTable(String table) {
        getSession().createSQLQuery(String.format(Constants.SQL_DROP, table)).executeUpdate();
    }

    /**
     * Remove all records from table
     *
     * @param table the table name
     */
    @Transactional
    public void cleanTable(String table) {
        getSession().createQuery(String.format(Constants.HQL_CLEAN, table)).executeUpdate();
    }

    /**
     * Get all table name in database
     */
    @Transactional
    public Set<String> getAllTables() {
        Set<String> tableNames = Sets.newHashSet();
        try {
            ResultSet result =
                    Objects.requireNonNull(getMetaData()).getTables(
                            null, null, null, new String[]{core.Constants.EntityDAO.TABLE});
            while (result.next()) {
                tableNames.add(result.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return tableNames;
    }

    /**
     * Get all table column
     *
     * @param table the table name
     */
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
}