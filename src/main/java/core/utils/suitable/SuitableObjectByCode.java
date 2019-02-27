package core.utils.suitable;

public interface SuitableObjectByCode<C, S extends HasSuitableObjectsByCode<? extends SuitableObjectByCode>> extends SuitableObject<C, S> {
}
