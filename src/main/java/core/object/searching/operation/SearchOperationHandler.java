package core.object.searching.operation;

import core.object.searching.SearchingService;
import core.utils.register.RegisteredObjectWithCode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface SearchOperationHandler<T extends SearchOperation> extends RegisteredObjectWithCode<T, SearchingService> {

    /**
     * Get predicate for searching
     *
     * @param builder criteria builder
     * @param field   the field for predicate
     * @param value   the searching params
     */
    Predicate getPredicate(CriteriaBuilder builder, Path field, T value);
}
