package core.utils.suitable;

import core.jpa.interfaces.HasCode;

public interface SuitableObjectByCode<C, S extends HasSuitableObjectsByCode<? extends SuitableObjectByCode>> extends HasCode, SuitableObject<C, S> {
}
