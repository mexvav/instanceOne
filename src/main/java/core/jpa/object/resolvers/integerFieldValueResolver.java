package core.jpa.object.resolvers;

import core.jpa.entity.fields.types.IntegerEntityFieldType;
import core.jpa.object.ObjectServiceException;

@SuppressWarnings("unused")
public class integerFieldValueResolver implements EntityFieldValueResolver<Integer, IntegerEntityFieldType>{

    @Override
    public Class<IntegerEntityFieldType> getSuitableType() {
        return IntegerEntityFieldType.class;
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
                throw new ObjectServiceException(e.getMessage());
            }
        }
        throw new ObjectServiceException(
                ObjectServiceException.ExceptionCauses.RESOLVING_IS_FAILED, getSuitableType().getName(), object.getClass().getName());
    }
}