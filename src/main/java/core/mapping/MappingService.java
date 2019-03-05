package core.mapping;

import com.google.common.collect.Sets;
import core.utils.ClassUtils;
import core.utils.register.AbstractRegisteringService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MappingService extends AbstractRegisteringService<Mapper> {

    private Set<Mapper> mappers;

    /**
     * Transform object to target class
     *
     * @param object the mapped object
     * @param to     the target class
     * @throws MappingException if mapper not found
     */
    @SuppressWarnings("unchecked")
    public <F, T> T mapping(final F object, final Class<T> to) {
        if(null == object){
            throw new MappingException(MappingException.ExceptionCauses.MAPPING_NULL);
        }
        Mapper<F, T> mapper = getMapper(object.getClass(), to);
        return mapper.transform(object, to);
    }

    /**
     * Get suitable mapper for transform objects from class "from" to "to"
     *
     * @param from the class for mapped object
     * @param to   the target class
     * @throws RuntimeException if mapper not found
     */
    @SuppressWarnings("unchecked")
    private <F, T> Mapper getMapper(final Class<F> from, final Class<T> to) {
        Set<Mapper> suitableMappers = getMappers().stream()
                .filter(mapper -> mapper.getFromClass().isAssignableFrom(from)
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
            int m1FromRate = fromClasses.get(m1.getFromClass());
            int m1ToRate = toClasses.get(m1.getToClass());

            int m2FromRate = fromClasses.get(m2.getFromClass());
            int m2ToRate = toClasses.get(m2.getToClass());

            int m1Rate = m1FromRate + m1ToRate;
            int m2Rate = m2FromRate + m2ToRate;
            return m1Rate - m2Rate;
        }).get();
    }

    /**
     * Get all registered mappers
     */
    private Set<Mapper> getMappers() {
        if (null == mappers) {
            mappers = Sets.newHashSet();
        }
        return mappers;
    }

    /**
     * Register mapper
     *
     * @param mapper the registered object
     */
    @Override
    public void register(Mapper mapper) {
        getMappers().add(mapper);
    }
}
