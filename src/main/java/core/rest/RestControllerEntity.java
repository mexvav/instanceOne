package core.rest;

import core.jpa.EntityBlank;
import core.jpa.EntityService;
import core.mapping.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/entity")
@Transactional
public class RestControllerEntity {

    @Autowired
    EntityService entityService;

    @Autowired
    MappingService mappingService;

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
}
