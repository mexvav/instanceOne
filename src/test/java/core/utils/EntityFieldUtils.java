package core.utils;

import core.jpa.entity.field.EntityField;
import core.jpa.interfaces.HasLength;

import java.util.Arrays;

public class EntityFieldUtils {

    public static int getLength(EntityField entityField) {
        FieldTestParam fieldTestParam = getFieldTestParam(entityField);

        int length = fieldTestParam.getDefaultLength();
        if (Arrays.asList(entityField.getClass().getInterfaces()).contains(HasLength.class)) {
            length = ((HasLength) entityField).getLength() != core.jpa.Constants.HasLength.DEFAUIT ?
                    ((HasLength) entityField).getLength() : length;
        }
        return length;
    }

    public static String getTableType(EntityField entityField) {
        return getFieldTestParam(entityField).getTableType();
    }

    public static FieldTestParam getFieldTestParam(EntityField entityField) {
        return getFieldTestParam(entityField.getType());
    }

    public static FieldTestParam getFieldTestParam(String entityFieldType) {
        return FieldTestParam.valueOf(entityFieldType.toUpperCase());
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
