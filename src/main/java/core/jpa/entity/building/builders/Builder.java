package core.jpa.entity.building.builders;

import core.jpa.entity.building.BuildingService;
import core.jpa.interfaces.HasSuitableClass;
import core.jpa.interfaces.HasSuitableClassObjects;
import net.bytebuddy.dynamic.DynamicType;

import javax.annotation.Nullable;

public interface Builder<B> extends HasSuitableClass<B, BuildingService> {

    /**
     * Step of class building. See {@link BuildingService#building(Object)}
     *
     * @param classBuilder entity class builder
     * @param buildObject  object for building
     * @throws core.jpa.entity.building.BuildingException if building is failed
     */
    DynamicType.Builder build(@Nullable final DynamicType.Builder classBuilder, B buildObject);
}
