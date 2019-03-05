package core.entity.building;

import core.utils.register.AbstractRegisteringServiceByClass;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.stereotype.Component;

/**
 * Service for building entity class in Runtime
 */
@Component
public class BuildingService extends AbstractRegisteringServiceByClass<Builder> {

    /**
     * Building Class in building chain, see {@link #build(DynamicType.Builder, Object)}
     *
     * @param object value for building in class
     * @throws BuildingException if building is failed
     */
    public Class<?> building(Object object) {
        try {
            return build(null, object)
                    .make()
                    .load(this.getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();
        } catch (Exception e) {
            throw new BuildingException(e);
        }
    }

    /**
     * Step of class building
     *
     * @param classBuilder entity class blank
     * @param buildObject  object for building
     * @return CtClass for generate to Entity
     * @throws BuildingException if building is failed
     */
    @SuppressWarnings("unchecked")
    public DynamicType.Builder build(final DynamicType.Builder classBuilder, Object buildObject) {
        Builder builder = get(buildObject);
        return builder.build(classBuilder, buildObject);
    }
}
