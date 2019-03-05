package core.entity.field.fields;

import com.google.common.collect.Maps;
import core.Constants;
import core.entity.EntityClass;
import core.entity.entities.Entity;
import core.entity.field.EntityFieldFactory;
import core.object.processing.DataObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityClassFactory;
import test_core.utils.RandomUtils;
import test_core.utils.SpringContextUtils;

import java.util.Map;

class LinkEntityFieldTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;
    private EntityFieldFactory fieldFactoty;

    @Autowired
    LinkEntityFieldTest(SpringContextUtils utils, EntityFieldFactory fieldFactoty) {
        this.utils = utils;
        this.fieldFactoty = fieldFactoty;
    }

    @Test
    void testCreateEntityWithLinkEntityField() {
        EntityClass entityForLink = EntityClassFactory.create();
        utils.getEntityService().createEntity(entityForLink);
        Class<Entity> entity = utils.getEntityService().getEntity(entityForLink.getCode());

        EntityClass entityWithLink = EntityClassFactory.create();
        LinkEntityField entityField = (LinkEntityField) fieldFactoty.get(Constants.EntityFieldType.LINK);
        entityField.setCode(RandomUtils.getCode());
        entityField.setLinkClass(entity);
        entityWithLink.addFields(entityField);

        utils.getEntityService().createEntity(entityWithLink);
    }

    @Test
    void testLinkEntityField() {
        EntityClass entityForLink = EntityClassFactory.create();
        utils.getEntityService().createEntity(entityForLink);
        Class<Entity> entity = utils.getEntityService().getEntity(entityForLink.getCode());

        EntityClass entityWithLink = EntityClassFactory.create();
        LinkEntityField linkField = (LinkEntityField) fieldFactoty.get(Constants.EntityFieldType.LINK);
        linkField.setCode(RandomUtils.getCode());
        linkField.setLinkClass(entity);
        entityWithLink.addFields(linkField);

        utils.getEntityService().createEntity(entityWithLink);

        DataObject objectForLink = utils.getObjectService().create(entityForLink.getCode(), Maps.newHashMap());

        Map<String, Object> params = Maps.newHashMap();
        params.put(linkField.getCode(), objectForLink);

        DataObject objectWithLink = utils.getObjectService().create(entityWithLink.getCode(), params);
        Object actual = objectWithLink.get(linkField.getCode());
        Assertions.assertEquals(objectForLink, actual);
    }
}
