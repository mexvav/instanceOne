package core;

import com.google.common.collect.Lists;
import core.jpa.EntityService;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeStringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/app")
@Transactional
public class Controller {

    @Autowired
    EntityService entityService;

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        return "Hello world";
    }

    @GetMapping("create")
    public String create() {
        Attribute attribute = new Attribute("value");
        attribute.setType(new AttributeStringType());
        entityService.createEntity("test1", Lists.newArrayList(attribute));
        return "true";
    }
}
