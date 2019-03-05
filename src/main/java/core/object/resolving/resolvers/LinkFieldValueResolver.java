package core.object.resolving.resolvers;

import core.entity.entities.Entity;
import core.entity.field.fields.LinkEntityField;
import core.object.processing.ContextFactory;
import core.object.processing.DataObject;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.object.resolving.ResolvingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Component
public class LinkFieldValueResolver extends AbstractFieldValueResolver<Entity, LinkEntityField> {

    private ProcessingService processingService;

    @Autowired
    LinkFieldValueResolver(ResolvingService resolvingService, ProcessingService processingService) {
        super(resolvingService, LinkEntityField.class);
        this.processingService = processingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity resolve(@Nullable Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof DataObject) {
            return getObjectByResultObject((DataObject) object);
        }
        if (object instanceof Entity) {
            return (Entity) object;
        }
        throw new RuntimeException();
    }

    private Entity getObjectByResultObject(DataObject resultObject) {
        ProcessorContext context = ContextFactory.get(resultObject.getEntityCode(), resultObject.getId());
        return processingService.processing(context).getObject();
    }
}