package core.jpa.entity.fields.types;

import java.beans.Transient;

public interface EntityFieldType {

    String getCode();

    @Transient
    Class<?> getFieldClass();

    boolean equals(Object o);

    int hashCode();
}
