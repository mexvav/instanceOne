package core.mapping;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.utils.ClassUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MappingService {

    private Set<Mapper> mappers;

    public void initMapper(Mapper mapper){
        getMappers().add(mapper);
    }

    /**
     * Exception for entity generator
     */
    static public class MappingException extends RuntimeException {
        enum ExceptionCauses {
            MAPPER_NOT_FOUND("Suitable mapper not found: %s -> %s");

            private String cause;

            ExceptionCauses(String cause) {
                this.cause = cause;
            }

            public String getCause() {
                return cause;
            }
        }

        MappingException(String message) {
            super(message);
        }

        MappingException(ExceptionCauses cause) {
            super(cause.getCause());
        }

        MappingException(ExceptionCauses cause, String... args) {
            super(String.format(cause.getCause(), args));
        }
    }

    /***
     * Mapping object
     * @param object - object for mapping
     * @param to - class to
     * @throws RuntimeException
     */
    public <F,T>T mapping(final F object, final Class<T> to){
        Mapper<F,T> mapper = getMapper((Class<F>)object.getClass(), to);
        if(null == mapper){
            throw new MappingException(MappingException.ExceptionCauses.MAPPER_NOT_FOUND,
                    object.getClass().getName(), to.getName());
        }
        return mapper.transform(object);
    }

    /***
     * Mapping object
     * @param from - class from
     * @param to - class to
     * @throws RuntimeException
     */
    @Nullable
    public <F,T> Mapper getMapper(final Class<F> from, final Class<T> to){
        Set<Mapper> suitableMappers = getMappers().stream()
                .filter(mapper -> mapper.getFromClass().isAssignableFrom(from) &&  mapper.getToClass().isAssignableFrom(to))
                .collect(Collectors.toSet());
        if(suitableMappers.isEmpty()){
            return null;
        }
        if(suitableMappers.size() == 1){
            return suitableMappers.iterator().next();
        }

        Map<Class,Integer> fromClasses = ClassUtils.getHierarchyClass(from);
        Map<Class,Integer> toClasses = ClassUtils.getHierarchyClass(to);

        return suitableMappers.stream().sorted((m1,m2)-> {
            int m1FromRate = fromClasses.get(m1.getFromClass());
            int m1ToRate = toClasses.get(m1.getToClass());

            int m2FromRate = fromClasses.get(m2.getFromClass());
            int m2ToRate = toClasses.get(m2.getToClass());

            int m1Rate = m1FromRate + m1ToRate;
            int m2Rate = m2FromRate + m2ToRate;

            return m1Rate - m2Rate;
        }).findFirst().get();
    }

    private Set<Mapper> getMappers(){
        if (null == mappers){
            mappers = Sets.newHashSet();
        }
        return mappers;
    }
}
