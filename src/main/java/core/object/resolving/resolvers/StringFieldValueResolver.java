package core.object.resolving.resolvers;

import core.entity.field.fields.StringEntityField;
import core.object.resolving.ResolvingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Component
public class StringFieldValueResolver extends AbstractFieldValueResolver<String, StringEntityField> {

    @Autowired
    StringFieldValueResolver(ResolvingService resolvingService) {
        super(resolvingService, StringEntityField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String resolve(@Nullable Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return object.toString();
    }
}