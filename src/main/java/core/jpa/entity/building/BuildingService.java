package core.jpa.entity.building;

import core.jpa.entity.building.builders.Builder;
import core.utils.AbstractHasSuitableClassObjects;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Service for build Class in Runtime
 */
@Component
public class BuildingService extends AbstractHasSuitableClassObjects<Builder> {

    public BuildingService() {
        initializeSuitableClassObjects();
    }

    protected String getPackage(){
        return getSuitableClass().getPackage().getName();
    }

    protected Class<Builder> getSuitableClass(){
        return Builder.class;
    }

    protected Consumer<Class<? extends Builder>> getSuitableClassConsumer(){
        return (builderClass) -> {
            try {
                Builder builder = builderClass.newInstance();
                builder.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BuildingException(e.getMessage());
            }
        };
    }

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
            throw new BuildingException(e.getMessage());
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
        Builder builder = getSuitableClassObject(buildObject);
        return builder.build(classBuilder, buildObject);
    }

}
