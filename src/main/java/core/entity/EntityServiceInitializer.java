package core.entity;

import core.dao.ObjectDAO;
import core.entity.entities.EntityDescription;
import core.mapping.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Init all entities from db by {@link EntityDescription}
 */
@SuppressWarnings("unused")
@Component
public class EntityServiceInitializer {

    private ObjectDAO dao;
    private MappingService mappingService;
    private EntityService entityService;

    @Autowired
    EntityServiceInitializer(ObjectDAO dao, MappingService mappingService, EntityService entityService) {
        this.dao = dao;
        this.mappingService = mappingService;
        this.entityService = entityService;
    }

    @SuppressWarnings("unchecked")
    @EventListener(ContextRefreshedEvent.class)
    public void initialize() {
        List<EntityDescription> entities = dao.getAll(EntityDescription.entityName);
        entityService.createEntity(entities.stream()
                .map(description -> mappingService.mapping(description.getDescription(), EntityClass.class))
                .toArray(EntityClass[]::new));
    }
}
