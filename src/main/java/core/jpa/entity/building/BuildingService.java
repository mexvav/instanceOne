package core.jpa.entity.building;

import com.google.common.collect.Sets;
import core.jpa.entity.building.builders.Builder;
import core.utils.ClassUtils;
import javassist.*;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for build CtClass
 * Service use javassist for generate CtClass
 */
@Component
public class BuildingService {

    private Set<Builder> builders;

    public BuildingService() {
        initializeBuilders();
    }

    /**
     * Register {@link Builder} for building Entity in chain building chain
     *
     * @param builder - initialized builder
     */
    public void initBuilder(Builder builder) {
        getBuilders().add(builder);
    }

    /**
     * Build in entity
     *
     * @param ctClass     entity class blank
     * @param buildObject object for building
     * @return CtClass for generate to Entity
     * @throws BuildingException if building is failed
     */
    @SuppressWarnings("unchecked")
    public CtClass build(final CtClass ctClass, Object buildObject) {
        Builder builder = getBuilder(buildObject);
        return builder.build(ctClass, buildObject);
    }

    /**
     * Get suitable builder
     *
     * @param buildObject object for building
     * @throws BuildingException if suitable builder not found
     */
    public Builder getBuilder(final Object buildObject) {
        @SuppressWarnings("unchecked")
        Set<Builder> suitableBuilders = getBuilders().stream()
                .filter(builder -> builder.getSuitableClass().isAssignableFrom(buildObject.getClass()))
                .collect(Collectors.toSet());
        if (suitableBuilders.isEmpty()) {
            throw new BuildingException(BuildingException.ExceptionCauses.SUITABLE_BUILDER_NOT_FOUND, buildObject.getClass().getName());
        }
        if (suitableBuilders.size() == 1) {
            return suitableBuilders.iterator().next();
        }
        Map<Class, Integer> fromClasses = ClassUtils.getHierarchyClass(buildObject.getClass());
        return suitableBuilders.stream()
                .min(Comparator.comparingInt(b -> fromClasses.get(b.getSuitableClass()))).get();
    }

    /**
     * Get all builders
     */
    private Set<Builder> getBuilders() {
        if (null == builders) {
            builders = Sets.newHashSet();
        }
        return builders;
    }

    /**
     * Initialize all default builders
     */
    private void initializeBuilders() {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends Builder>> builders = reflections.getSubTypesOf(Builder.class);
        builders.forEach(builder -> {
            if (!Modifier.isAbstract(builder.getModifiers()) && !builder.isInterface()) {
                try {
                    Builder builderInsctance = builder.newInstance();
                    builderInsctance.init(this);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BuildingException(e.getMessage());
                }
            }
        });
    }
}
