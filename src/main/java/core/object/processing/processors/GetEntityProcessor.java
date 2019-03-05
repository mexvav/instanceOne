package core.object.processing.processors;

import core.Constants;
import core.entity.EntityService;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Component
public class GetEntityProcessor extends AbstractProcessor {

    private EntityService entityService;

    @Autowired
    GetEntityProcessor(ProcessingService processingService, EntityService entityService){
        super(processingService, Constants.Processing.GET_ENTITY);
        this.entityService = entityService;
    }

    /**
     * Set entity class for object processing
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        setEntityClass(context);
        setEntity(context);
    }

    /**
     * Set {@link core.entity.EntityClass} for object processing
     *
     * @param context the values for object processing
     */
    private void setEntityClass(@NotNull final ProcessorContext context) {
        if (null != context.getEntityClass()) {
            return;
        }
        String entityCode = context.getEntityCode();
        if (null == entityCode) {
            throw new RuntimeException();
        }
        if (!entityService.isEntityExist(entityCode)) {
            throw new RuntimeException();
        }
        context.setEntityClass(entityService.getEntityBlank(entityCode));
    }

    /**
     * Set Entity for object processing
     *
     * @param context the values for object processing
     */
    private void setEntity(@NotNull final ProcessorContext context) {
        if (null != context.getEntity()) {
            return;
        }
        String entityCode = null == context.getEntityClass() ?
                context.getEntityCode() : context.getEntityClass().getCode();
        if (null == entityCode) {
            throw new RuntimeException();
        }
        if (!entityService.isEntityExist(entityCode)) {
            throw new RuntimeException();
        }
        context.setEntity(entityService.getEntity(entityCode));
    }
}
