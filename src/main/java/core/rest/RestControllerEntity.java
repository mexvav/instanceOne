package core.rest;

import com.google.common.collect.Maps;
import core.jpa.entity.EntityBlank;
import core.jpa.entity.EntityService;
import core.jpa.object.ObjectService;
import core.jpa.mapping.MappingService;
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
        EntityBlank entityBlank = mappingService.mapping(jsonEntity, EntityBlank.class);
        entityService.createEntity(entityBlank);
        return "true";
    }

    @GetMapping("/test")
    public String create() {
        Map<String, Object> objectMap = Maps.newHashMap();
        objectMap.put("string", "test1");
        objectMap.put("date", new Date());
        objectMap.put("integer", 123);

        objectService.createObject("test", objectMap);
        return "true";
    }

    @GetMapping("/zzz")
    @Transactional
    public String zzz(@RequestParam String sql) {
        List z = sessionFactory.getCurrentSession().createQuery(sql).list();
        return z.toString();
    }

}
