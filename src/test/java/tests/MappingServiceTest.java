package tests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.jpa.entity.EntityClass;
import core.jpa.entity.fields.EntityField;
import core.jpa.mapping.MappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MappingServiceTest {

    private static MappingService mappingService;

    @BeforeAll
    static void initialize() {
        mappingService = new MappingService();
    }

    @Test
    void testMappingEntityBlank() {
        EntityClass entityClass = EntityClassFactory.create();
        String json = mappingService.mapping(entityClass, String.class);
        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);

        Assertions.assertEquals(entityClass, mappedEntityClass);
    }

    @Test
    void test() throws JsonProcessingException {
        EntityClass entityClass = EntityClassFactory.create();

        //EntityField stringEntityField = EntityFieldFactory.stringEntityField();
        //EntityField dateEntityField = EntityFieldFactory.dateEntityField();
        //EntityField integerEntityField = EntityFieldFactory.integerEntityField();
        //entityClass.addFields(stringEntityField, dateEntityField, integerEntityField);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //String json1 = mapper.writeValueAsString(stringEntityField);
        //String json2 = mapper.writeValueAsString(dateEntityField);
        //String json3 = mapper.writeValueAsString(integerEntityField);
        String json = mapper.writeValueAsString(mapper);
    }


}
