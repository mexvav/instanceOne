package core.jpa.dao;

import core.jpa.entity.entities.NamedEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DAOEntity {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(Object object) {
        sessionFactory.getCurrentSession().persist(object);
    }

    @Transactional
    public <T extends NamedEntity> T get(Class<T> entityClass, double id) {
        return getSession().get(entityClass, id);
    }

    @Transactional
    public List getAll(String code){
        return getSession().createQuery("FROM ".concat(code)).list();
    }

    private Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
