package core.entity.building;

import core.utils.suitable.SuitableObjectByClass;
import net.bytebuddy.dynamic.DynamicType;

import javax.annotation.Nullable;

public interface Builder<B> extends SuitableObjectByClass<B, BuildingService> {

    /**
     * Step of class building. See {@link BuildingService#building(Object)}
     *
     * @param classBuilder entity class builder
     * @param buildObject  object for building
     * @throws core.entity.building.BuildingException if building is failed
     */
    DynamicType.Builder build(@Nullable final DynamicType.Builder classBuilder, B buildObject);
}