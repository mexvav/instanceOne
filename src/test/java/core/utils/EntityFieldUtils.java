package core.utils;

import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.interfaces.HasLength;

import java.util.Arrays;

public class EntityFieldUtils {

    public static int getLength(EntityField entityField) {
        EntityFieldType entityFieldType = entityField.getType();
        FieldTestParam fieldTestParam = getFieldTestParam(entityFieldType);

        int length = fieldTestParam.getDefaultLength();
        if (Arrays.asList(entityFieldType.getClass().getInterfaces()).contains(HasLength.class)) {
            length = ((HasLength) entityFieldType).getLength() != core.jpa.Constants.HasLength.DEFAUIT ?
                    ((HasLength) entityFieldType).getLength() : length;
        }
        return length;
    }

    public static String getTableType(EntityField entityField) {
        return getFieldTestParam(entityField).getTableType();
    }

    public static String getTableType(EntityFieldType entityFieldType) {
        return getFieldTestParam(entityFieldType).getTableType();
    }

    public static FieldTestParam getFieldTestParam(EntityField entityField) {
        return getFieldTestParam(entityField.getType());
    }

    public static FieldTestParam getFieldTestParam(EntityFieldType entityFieldType) {
        return FieldTestParam.valueOf(entityFieldType.getCode().toUpperCase());
    }

    /**
     * Parameters for table columns
     */
    public enum FieldTestParam {
        STRING("varchar", 255),
        INTEGER("int4", 10),
        DATE("timestamp", 29);

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
