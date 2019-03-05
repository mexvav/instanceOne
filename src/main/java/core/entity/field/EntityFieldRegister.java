package core.entity.field;

import core.entity.field.fields.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class EntityFieldRegister {

    @Autowired
    EntityFieldRegister(EntityFieldFactory fieldFactoty) {
        fieldFactoty.register(new DateEntityField());
        fieldFactoty.register(new IntegerEntityField());
        fieldFactoty.register(new StringEntityField());
        fieldFactoty.register(new BooleanEntityField());
        fieldFactoty.register(new LinkEntityField());
    }
}
