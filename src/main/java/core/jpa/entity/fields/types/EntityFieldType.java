package core.jpa.entity.fields.types;

public interface EntityFieldType {

    String getCode();

    Class<?> getFieldClass();
}
