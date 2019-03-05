package core.object.resolving.resolvers;

import core.entity.field.fields.BooleanEntityField;
import core.object.resolving.ResolvingException;
import core.object.resolving.ResolvingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Component
public class BooleanFieldValueResolver extends AbstractFieldValueResolver<Boolean, BooleanEntityField> {

    @Autowired
    BooleanFieldValueResolver(ResolvingService resolvingService) {
        super(resolvingService, BooleanEntityField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean resolve(@Nullable Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        if (object instanceof Integer) {
            return ((Integer) object) > 0;
        }
        if (object instanceof String) {
            try {
                return Boolean.valueOf((String) object);
            } catch (NumberFormatException e) {
                throw new ResolvingException(ResolvingException.ExceptionCauses.RESOLVING_IS_FAILED,
                        object.toString(), getSuitableClass().getName());
            }
        }
        throw new ResolvingException(
                ResolvingException.ExceptionCauses.RESOLVING_IS_FAILED,
                object.toString(),
                getSuitableClass().getName());
    }
}