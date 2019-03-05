package core.dao;

import core.interfaces.HasEntityCode;
import core.interfaces.HasId;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class ObjectDAO extends AbstractDAO {

    @Autowired
    protected ObjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Get object
     *
     * @param entityClass class of entity
     * @param id          the unique id, see {@link HasId#getId()}
     */
    @Transactional
    public <T extends HasEntityCode> T get(Class<T> entityClass, long id) {
        return getSession().get(entityClass, id);
    }

    /**
     * Get object
     *
     * @param entityClass class of entity
     */
    @Transactional
    public <T extends HasEntityCode> List<T> find(Class<T> entityClass, String field, Object value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.where(builder.equal(root.get(field), value));
        return getSession().createQuery(query).list();
    }

    /**
     * Get all object
     *
     * @param code code of entity, see {@link HasEntityCode#getEntityCode()}
     */
    @Transactional
    public List getAll(String code) {
        return getSession().createQuery(Constants.HQL_FROM.concat(code)).list();
    }

    /**
     * Remove object
     *
     * @param object removing object
     */
    @Transactional
    public void remove(Object object) {
        getSession().remove(object);
    }

    /**
     * Save object
     *
     * @param object removing object
     */
    @Transactional
    public void save(Object object) {
        getSession().save(object);
    }
}
