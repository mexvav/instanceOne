package core.jpa.object.resolving.resolvers;

import core.jpa.entity.field.fields.StringEntityField;

@SuppressWarnings("unused")
public class StringFieldValueResolver extends AbstractFieldValueResolver<String, StringEntityField>{

    @Override
    public Class<StringEntityField> getSuitableClass() {
        return StringEntityField.class;
    }

    @Override
    public String resolve(Object object){
        if(object instanceof String){
            return (String) object;
        }
        return object.toString();
    }
}