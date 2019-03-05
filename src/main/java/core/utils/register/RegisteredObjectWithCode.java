package core.utils.register;

import core.interfaces.HasCode;

public interface RegisteredObjectWithCode<C, S extends RegisteringServiceByCode<? extends RegisteredObjectWithCode>> extends RegisteredObject<C, S>, HasCode {

}
