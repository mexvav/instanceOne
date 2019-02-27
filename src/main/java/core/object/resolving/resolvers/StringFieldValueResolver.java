package core.object.resolving.resolvers;

import core.entity.field.fields.StringEntityField;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class StringFieldValueResolver extends AbstractFieldValueResolver<String, StringEntityField> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<StringEntityField> getSuitableClass() {
        return StringEntityField.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String resolve(@Nullable Object object) {
        if(null == object){
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return object.toString();
    }
}