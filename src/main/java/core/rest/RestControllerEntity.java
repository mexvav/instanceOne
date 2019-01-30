package core.rest;

import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.EntityService;
import core.jpa.object.ObjectService;
import core.mapping.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
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

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        return "";
    }

    @PostMapping("/create")
    public String createEntity(@RequestBody String jsonEntity){
        EntityBlank entityBlank = mappingService.mapping(jsonEntity, EntityBlank.class);
        entityService.createEntity(entityBlank);
        return "true";
    }

    @GetMapping("/test")
    public String create(){
        Map<String, Object> objectMap = Maps.newHashMap();
        objectMap.put("string", "test1");
        objectMap.put("date", new Date());
        objectMap.put("integer", 123);

        //objectService.createObject("test", objectMap);
        objectService.test();
        return "true";
    }

    /*
    {
        "code":"test",
        "attributes":[
        {
            "code":"string",
            "unique":false,
            "type":{"code":"string"},
            "required":false
        },
        {
            "code":"date",
            "unique":false,
            "type":{"code":"date"},
            "required":false
        },
        {
            "code":"integer",
            "unique":false,
            "type":{"code":"integer"},
            "required":false
        }]
    }
    */
}
