package core.object.searching.operation;

import core.Constants;
import core.object.searching.SearchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@SuppressWarnings("unused")
@Component
public class LikeOperationHandler extends AbstractSearchOperationHandler<LikeOperation> {

    @Autowired
    public LikeOperationHandler(SearchingService service) {
        super(service, Constants.SearchingService.LIKE_OPERATION);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate getPredicate(CriteriaBuilder builder, Path field, LikeOperation value) {
        return builder.like(field, "%" + value.getValue() + "%");
    }
}