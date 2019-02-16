package core.utils;

import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.interfaces.HasLength;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class EntityFieldUtils {

    /**
     * Check set of {@link EntityField}, see {@link EntityFieldUtils#checkField}
     *
     * @param entityFields fields from {@link core.jpa.entity.EntityClass}
     * @param columns      result {@link core.jpa.dao.EntityDAO#getColumns(String)}
     * @throws AssertionFailedError if check is failed
     */
    public static void checkFields(Set<EntityField> entityFields, Set<Map<String, String>> columns) {
        for (EntityField entityField : entityFields) {
            checkField(entityField, columns);
        }
    }

    /**
     * Check {@link EntityField} in table column
     * is column {@link EntityField} exist in table
     * is column nullable if {@link EntityField#isRequired()} != true
     * is column type suitable {@link EntityFieldType}
     * is column length equals {@link HasLength#getLength()}
     *
     * @param entityField field from {@link core.jpa.entity.EntityClass}
     * @param columns     result from {@link core.jpa.dao.EntityDAO#getColumns(String)}
     * @throws AssertionFailedError if check is failed
     */
    public static void checkField(final EntityField entityField, final Set<Map<String, String>> columns) {
        Map<String, String> columnValues = columns.stream().filter(column ->
                entityField.getCode().equals(column.get(core.jpa.Constants.EntityDAO.COLUMN_NAME))
        ).findFirst().orElseThrow(() ->
                new AssertionError(String.format(Constants.Error.COLUMN_NOT_EXIST, entityField.getCode())));

        EntityFieldType entityFieldType = entityField.getType();
        FieldTestParam fieldTestParam = FieldTestParam.valueOf(entityFieldType.getCode().toUpperCase());

        int length = fieldTestParam.getDefaultLength();
        if (Arrays.asList(entityFieldType.getClass().getInterfaces()).contains(HasLength.class)) {
            length = ((HasLength) entityFieldType).getLength() != core.jpa.Constants.HasLength.DEFAUIT ?
                    ((HasLength) entityFieldType).getLength() : length;
        }

        Assertions.assertEquals(entityField.isRequired(),
                core.jpa.Constants.EntityDAO.NO.equals(columnValues.get(core.jpa.Constants.EntityDAO.IS_NULLABLE)));
        Assertions.assertEquals(fieldTestParam.getTableType(),
                columnValues.get(core.jpa.Constants.EntityDAO.TYPE_NAME));
        Assertions.assertEquals(String.valueOf(length),
                columnValues.get(core.jpa.Constants.EntityDAO.COLUMN_SIZE));
    }

    /**
     * Parameters for table columns
     */
    enum FieldTestParam {
        STRING("varchar", 255),
        INTEGER("int8", 19),
        DATE("", 12);

        /**
         * Default length column in table
         */
        private int defaultLength;
        /**
         * Type columns in table
         */
        private String tableType;

        FieldTestParam(String tableType, int defaultLength) {
            this.tableType = tableType;
            this.defaultLength = defaultLength;
        }

        public String getTableType() {
            return tableType;
        }

        public int getDefaultLength() {
            return defaultLength;
        }
    }
}
