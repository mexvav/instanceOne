package core.jpa.object.resolving.resolvers;

import core.jpa.entity.field.fields.IntegerEntityField;

import core.jpa.object.ObjectServiceException;

@SuppressWarnings("unused")
public class integerFieldValueResolver extends AbstractFieldValueResolver<Integer, IntegerEntityField>{

    @Override
    public Class<IntegerEntityField> getSuitableClass() {
        return IntegerEntityField.class;
    }

    @Override
    public Integer resolve(Object object){
        if(object instanceof Integer){
            return (Integer) object;
        }
        if(object instanceof String){
            try{
                return Integer.valueOf((String)object);
            }
            catch (NumberFormatException e){
                throw new ObjectServiceException(e);
            }
        }
        throw new ObjectServiceException(
                ObjectServiceException.ExceptionCauses.RESOLVING_IS_FAILED, getSuitableClass().getName(), object.getClass().getName());
    }
}