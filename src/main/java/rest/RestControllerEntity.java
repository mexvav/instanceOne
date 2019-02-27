package rest;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.EntityService;
import core.entity.field.fields.DateEntityField;
import core.entity.field.EntityField;
import core.entity.field.fields.IntegerEntityField;
import core.entity.field.fields.StringEntityField;
import core.mapping.MappingService;
import core.object.ObjectService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entity")
public class RestControllerEntity {

    @Autowired
    EntityService entityService;

    @Autowired
    MappingService mappingService;

    @Autowired
    ObjectService objectService;

    @Autowired
    SessionFactory sessionFactory;

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        return "";
    }

    @PostMapping("/create")
    public String createEntity(@RequestBody String jsonEntity) {
        //EntityClass entityClass = mappingService.mapping(jsonEntity, EntityClass.class);
        EntityClass entityClass = new EntityClass();
        entityClass.setCode("zzz");

        EntityField field1 = new StringEntityField();
        field1.setCode("z1");
        EntityField field2 = new DateEntityField();
        field2.setCode("z2");
        EntityField field3 = new IntegerEntityField();
        field3.setCode("z3");

        entityClass.addFields(field1, field2, field3);
        entityService.createEntity(entityClass);
        return "true";
    }

    @GetMapping("/test")
    public String create() {
        Map<String, Object> objectMap = Maps.newHashMap();
        objectMap.put("string", "test1");
        objectMap.put("date", new Date());
        objectMap.put("integer", 123);

        objectService.create("test", objectMap);
        return "true";
    }

    @GetMapping("/zzz")
    @Transactional
    public String zzz(@RequestParam String sql) {
        List z = sessionFactory.getCurrentSession().createQuery(sql).list();
        return z.toString();
    }
}
