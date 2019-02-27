package core.object.processing.processors;

import core.Constants;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class GetEntityProcessor extends AbstractProcessor {

    @Override
    public String getProcessCode() {
        return Constants.Processing.GET_ENTITY;
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
        if (!processingService.getEntityService().isEntityExist(entityCode)) {
            throw new RuntimeException();
        }
        context.setEntityClass(processingService.getEntityService().getEntityBlank(entityCode));
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
        if (!processingService.getEntityService().isEntityExist(entityCode)) {
            throw new RuntimeException();
        }
        context.setEntity(processingService.getEntityService().getEntity(entityCode));
    }
}
