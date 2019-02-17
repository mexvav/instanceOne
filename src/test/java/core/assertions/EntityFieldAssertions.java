package core.assertions;

import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.interfaces.HasLength;
import core.utils.Constants;
import core.utils.EntityFieldUtils;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.util.Map;
import java.util.Set;

public class EntityFieldAssertions {

    /**
     * Check set of {@link EntityField}, see {@link EntityFieldAssertions#assertField}
     *
     * @param entityFields fields from {@link core.jpa.entity.EntityClass}
     * @param columns      result {@link core.jpa.dao.EntityDAO#getColumns(String)}
     * @throws AssertionFailedError if check is failed
     */
    public static void assertFields(Set<EntityField> entityFields, Set<Map<String, String>> columns) {
        for (EntityField entityField : entityFields) {
            assertField(entityField, columns);
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
    public static void assertField(final EntityField entityField, final Set<Map<String, String>> columns) {
        Map<String, String> columnValues = columns.stream().filter(column ->
                entityField.getCode().equals(column.get(core.jpa.Constants.EntityDAO.COLUMN_NAME))
        ).findFirst().orElseThrow(() ->
                new AssertionError(String.format(Constants.Error.COLUMN_NOT_EXIST, entityField.getCode())));

        Assertions.assertEquals(entityField.isRequired(),
                core.jpa.Constants.EntityDAO.NO.equals(columnValues.get(core.jpa.Constants.EntityDAO.IS_NULLABLE)));
        Assertions.assertEquals(EntityFieldUtils.getTableType(entityField),
                columnValues.get(core.jpa.Constants.EntityDAO.TYPE_NAME));
        Assertions.assertEquals(String.valueOf(EntityFieldUtils.getLength(entityField)),
                columnValues.get(core.jpa.Constants.EntityDAO.COLUMN_SIZE));
    }
}
