package core.jpa.mapping;

import com.google.common.collect.Sets;
import core.jpa.mapping.mappers.Mapper;
import core.utils.ClassUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MappingService {

    private Set<Mapper> mappers;

    public MappingService() {
        initializeMappers();
    }

    /**
     * Register mapper in service
     *
     * @param mapper mapper
     */
    public void initMapper(Mapper mapper) {
        getMappers().add(mapper);
    }

    /***
     * Mapping object
     * @param object - object for mapping
     * @param to - class to
     * @throws MappingException if mapper not found
     */
    public <F, T> T mapping(final F object, final Class<T> to) {
        @SuppressWarnings("unchecked")
        Mapper<F, T> mapper = getMapper(object.getClass(), to);
        if (null == mapper) {
            throw new MappingException(MappingException.ExceptionCauses.MAPPER_NOT_FOUND,
                    object.getClass().getName(), to.getName());
        }
        return mapper.transform(object);
    }

    /***
     * Mapping object
     * @param from - class from
     * @param to - class to
     * @throws RuntimeException if mapper not found
     */
    @Nullable
    private <F, T> Mapper getMapper(final Class<F> from, final Class<T> to) {
        @SuppressWarnings("unchecked")
        Set<Mapper> suitableMappers = getMappers().stream()
                .filter(mapper -> mapper.getFromClass().isAssignableFrom(from) && mapper.getToClass().isAssignableFrom(to))
                .collect(Collectors.toSet());
        if (suitableMappers.isEmpty()) {
            return null;
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

    private Set<Mapper> getMappers() {
        if (null == mappers) {
            mappers = Sets.newHashSet();
        }
        return mappers;
    }

    /**
     * Initialize all default mappers
     */
    private void initializeMappers() {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends Mapper>> mappers = reflections.getSubTypesOf(Mapper.class);
        mappers.forEach(mapper -> {
            if (!Modifier.isAbstract(mapper.getModifiers()) && !mapper.isInterface()) {
                try {
                    Mapper mapperInstance = mapper.newInstance();
                    mapperInstance.init(this);
                    initMapper(mapperInstance);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new MappingException(e.getMessage());
                }
            }
        });
    }
}
