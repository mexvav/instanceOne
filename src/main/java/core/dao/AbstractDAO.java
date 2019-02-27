package core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Objects;

public class AbstractDAO {

    private SessionFactory sessionFactory;

    protected AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Connection getConnection() throws SQLException {
        return sessionFactory
                .getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(ConnectionProvider.class)
                .getConnection();
    }

    protected DatabaseMetaData getMetaData() throws SQLException {
        return Objects.requireNonNull(getConnection()).getMetaData();
    }
}
