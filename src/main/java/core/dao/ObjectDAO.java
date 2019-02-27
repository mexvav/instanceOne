package core.dao;

import core.interfaces.HasEntityCode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ObjectDAO extends AbstractDAO {

    @Autowired
    protected ObjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Transactional
    public <T extends HasEntityCode> T get(Class<T> entityClass, long id) {
        return getSession().get(entityClass, id);
    }

    @Transactional
    public List getAll(String code) {
        return getSession().createQuery(Constants.HQL_FROM.concat(code)).list();
    }

    @Transactional
    public void remove(Object object) {
        getSession().remove(object);
    }

    @Transactional
    public void save(Object object) {
        getSession().save(object);
    }
}
