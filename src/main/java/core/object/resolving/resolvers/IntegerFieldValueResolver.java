package core.object.resolving.resolvers;

import core.entity.field.fields.IntegerEntityField;
import core.object.resolving.ResolvingException;
import core.object.resolving.ResolvingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Component
public class IntegerFieldValueResolver extends AbstractFieldValueResolver<Integer, IntegerEntityField> {

    @Autowired
    IntegerFieldValueResolver(ResolvingService resolvingService) {
        super(resolvingService, IntegerEntityField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer resolve(@Nullable Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        if (object instanceof String) {
            try {
                return Integer.valueOf((String) object);
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