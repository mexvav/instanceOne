package core.jpa.object.resolvers;

import core.jpa.entity.fields.types.StringEntityFieldType;

@SuppressWarnings("unused")
public class StringFieldValueResolver implements EntityFieldValueResolver<String, StringEntityFieldType>{

    @Override
    public Class<StringEntityFieldType> getSuitableType() {
        return StringEntityFieldType.class;
    }

    @Override
    public String resolve(Object object){
        if(object instanceof String){
            return (String) object;
        }
        return object.toString();
    }
}