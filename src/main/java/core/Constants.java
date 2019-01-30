package core;

public class Constants {
    /*Database property*/
    public static final String dbPropertyDriverClassName = "datasource.driver-class-name";
    public static final String dbPropertyUrl = "datasource.url";
    public static final String dbPropertyUsername = "datasource.username";
    public static final String dbPropertyPassword = "datasource.password";

    /*JPA/Hibernate property*/
    public static final String dbPropertyShowSql = "jpa.show-sql";
    public static final String dbPropertyDialect = "jpa.properties.hibernate.dialect";
    public static final String dbPropertyCurSCtx = "jpa.properties.hibernate.current_session_context_class";
    public static final String dbPropertyDdlAuto = "jpa.hibernate.ddl-auto";
    public static final String dbPropertyMetaDef = "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults";

    public static final String hibernatePropShowSql = "hibernate.show_sql";
    public static final String hibernatePropDialect = "hibernate.dialect";
    public static final String hibernatePropCurSCtx = "hibernate.current_session_context_class";
    public static final String hibernatePropHbm2ddl = "hibernate.hbm2ddl.auto";
    public static final String hibernatePropMetaDef = "hibernate.temp.use_jdbc_metadata_defaults";

    public static final String dbEntityPrefix = "entity_";
}
