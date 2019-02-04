package core.jpa;

public class Constants {

    public static class JPA{

        /*Database property*/
        public static final String DB_PROPERTY_DRIVER_CLASS_NAME = "datasource.driver-class-name";
        public static final String DB_PROPERTY_URL = "datasource.url";
        public static final String DB_PROPERTY_USERNAME = "datasource.username";
        public static final String DB_PROPERTY_PASSWORD = "datasource.password";

        /*JPA/Hibernate property*/
        public static final String DB_PROPERTY_SHOW_SQL = "jpa.show-sql";
        public static final String DB_PROPERTY_DIALECT = "jpa.properties.hibernate.dialect";
        public static final String DB_PROPERTY_CUR_S_CTX = "jpa.properties.hibernate.current_session_context_class";
        public static final String DB_PROPERTY_DDL_AUTO = "jpa.hibernate.ddl-auto";
        public static final String DB_PROPERTY_META_DEF = "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults";
        public static final String HIBERNATE_PROP_SHOW_SQL = "hibernate.show_sql";
        public static final String HIBERNATE_PROP_DIALECT = "hibernate.dialect";
        public static final String HIBERNATE_PROP_CUR_S_CTX = "hibernate.current_session_context_class";
        public static final String HIBERNATE_PROP_HBM_2_DDL = "hibernate.hbm2ddl.auto";
        public static final String HIBERNATE_PROP_META_DEF = "hibernate.temp.use_jdbc_metadata_defaults";
    }

    public static class HasCode{
        public static final String CODE = "code";
    }

    public static class HasTitle{
        public static final String TITLE = "title";
    }

    public static class HasId{
        public static final String ID = "id";
    }

    public static class Entity{
        public static final String ATTRIBUTES = "attributes";
    }

    public static class Attribute{
        public static final String REQUIRED = "required";
        public static final String UNIQUE = "unique";
        public static final String TYPE = "type";
    }

    public static class AttributeType{
        public static final String DATE = "date";
        public static final String INTEGER = "integer";
        public static final String STRING = "string";
    }

    public static class Builder{
        public static final String GETTER = "get";
        public static final String SETTER = "set";
    }

    public static class EntityDescription{
        public static final String ENTITY_NAME = "EntityDescription";
        public static final String DESCRIPTION = "description";
        public static final String TABLE = DB_ENTITY_PREFIX + DESCRIPTION;
    }



    public static final String DB_ENTITY_PREFIX = "entity_";
}
