package core.object.processing;

import com.google.common.collect.Maps;
import core.entity.entities.Entity;
import core.object.ObjectServiceException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DataObjectWrapper implements DataObject {

    private Entity target;
    private Set<String> propertyNames;

    public DataObjectWrapper(Entity target) {
        this.target = target;
        propertyNames = Arrays.stream(target.getClass().getDeclaredFields())
                .map(Field::getName).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> propertyNames() {
        return propertyNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(String key) {
        try {
            Field field = target.getClass().getDeclaredField(key);
            field.setAccessible(true);
            Object value = field.get(target);
            if (value instanceof Entity) {
                return new DataObjectWrapper((Entity) value);
            }
            return value;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ObjectServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = Maps.newHashMap();
        for (String property : propertyNames()) {
            properties.put(property, get(property));
        }
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityCode() {
        return target.getEntityCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return target.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(Long id) {
        target.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataObjectWrapper)) return false;
        DataObjectWrapper that = (DataObjectWrapper) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getEntityCode(), that.getEntityCode())
                && Objects.equals(getProperties(), that.getProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(target.getEntityCode(), target.getId(), getProperties());
    }
}
