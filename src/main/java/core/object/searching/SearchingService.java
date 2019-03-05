package core.object.searching;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.entity.EntityService;
import core.entity.entities.Entity;
import core.object.ObjectServiceException;
import core.object.searching.operation.SearchOperation;
import core.object.searching.operation.SearchOperationHandler;
import core.utils.register.RegisteringServiceByCode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SearchingService implements RegisteringServiceByCode<SearchOperationHandler> {

    private SessionFactory sessionFactory;
    private Map<String, SearchOperationHandler> operations;

    @Autowired
    SearchingService(SessionFactory sessionFactory, EntityService entityService) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Searching objects
     *
     * @param entity the
     * @param params the values for searching
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<? extends Entity> search(Class entity, Map<String, Object> params) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(entity);
        Root root = query.from(entity);
        Set<Predicate> expressions = Sets.newHashSet();
        params.forEach((key, value) ->
                expressions.add(getPredicate(builder, root.get(key), value))
        );
        Predicate where = builder.and(expressions.toArray(new Predicate[0]));
        query.where(where);
        return sessionFactory.getCurrentSession().createQuery(query).list();
    }

    @SuppressWarnings("unchecked")
    private Predicate getPredicate(CriteriaBuilder builder, Path field, Object value) {
        if (value instanceof SearchOperation) {
            SearchOperation searchOperation = (SearchOperation) value;
            return get(searchOperation.getCode()).getPredicate(builder, field, searchOperation);
        } else {
            if (null == value) {
                return builder.isNull(field);
            }
            return builder.equal(field, value);
        }
    }

    /**
     * Get suitable search operation handler
     *
     * @param code the operation code
     */
    @Override
    public SearchOperationHandler get(String code) {
        if (!getOperationHandlers().containsKey(code)) {
            throw new ObjectServiceException(ObjectServiceException.ExceptionCauses.SEARCHING_OPERATON_NOT_REGISTERED, code);
        }
        return getOperationHandlers().get(code);
    }

    /**
     * Register {@link SearchOperationHandler}
     *
     * @param handler the search operation handler
     */
    @Override
    public void register(SearchOperationHandler handler) {
        getOperationHandlers().put(handler.getCode(), handler);
    }

    /**
     * Get all registered operation handlers
     */
    private Map<String, SearchOperationHandler> getOperationHandlers() {
        if (null == operations) {
            operations = Maps.newHashMap();
        }
        return operations;
    }
}
