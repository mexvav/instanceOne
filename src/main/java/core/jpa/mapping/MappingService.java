package core.jpa.mapping;

import core.jpa.entity.building.BuildingException;
import core.utils.ClassUtils;
import core.utils.suitable.AbstractHasSuitableObjectsByClass;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class MappingService extends AbstractHasSuitableObjectsByClass<Mapper> {

    public MappingService() {
        initializeSuitableObjects();
    }

    protected String getPackage() {
        return getObjectClass().getPackage().getName();
    }

    protected Class<Mapper> getObjectClass() {
        return Mapper.class;
    }

    protected Consumer<Class<? extends Mapper>> getSuitableObjectClassConsumer() {
        return (mapperClass) -> {
            try {
                Mapper mapper = mapperClass.newInstance();
                mapper.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new MappingException(e);
            }
        };
    }

    /**
     * Mapping object to target class
     *
     * @param object - object for mapping
     * @param to     - target class
     * @throws MappingException if mapper not found
     */
    @SuppressWarnings("unchecked")
    public <F, T> T mapping(final F object, final Class<T> to) {
        Mapper<F, T> mapper = getMapper(object.getClass(), to);
        return mapper.transform(object, to);
    }

    /**
     * Get suitable mapper for mapping objects with class "from" to "to"
     *
     * @param from class from
     * @param to   class to
     * @throws RuntimeException if mapper not found
     */
    @SuppressWarnings("unchecked")
    private <F, T> Mapper getMapper(final Class<F> from, final Class<T> to) {
        Set<Mapper> suitableMappers = getSuitableClassObjects().stream()
                .filter(mapper -> mapper.getSuitableClass().isAssignableFrom(from)
                        && mapper.getToClass().isAssignableFrom(to))
                .collect(Collectors.toSet());
        if (suitableMappers.isEmpty()) {
            throw new MappingException(MappingException.ExceptionCauses.MAPPER_NOT_FOUND,
                    from.getName(),
                    to.getName());
        }
        if (suitableMappers.size() == 1) {
            return suitableMappers.iterator().next();
        }
        Map<Class, Integer> fromClasses = ClassUtils.getHierarchyClass(from);
        Map<Class, Integer> toClasses = ClassUtils.getHierarchyClass(to);
        return suitableMappers.stream().min((m1, m2) -> {
            int m1FromRate = fromClasses.get(m1.getSuitableClass());
            int m1ToRate = toClasses.get(m1.getToClass());

            int m2FromRate = fromClasses.get(m2.getSuitableClass());
            int m2ToRate = toClasses.get(m2.getToClass());

            int m1Rate = m1FromRate + m1ToRate;
            int m2Rate = m2FromRate + m2ToRate;
            return m1Rate - m2Rate;
        }).get();
    }
}
