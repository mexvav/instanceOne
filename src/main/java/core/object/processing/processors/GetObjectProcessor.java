package core.object.processing.processors;

import core.Constants;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class GetObjectProcessor extends AbstractProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProcessCode() {
        return Constants.Processing.GET_OBJECT;
    }

    /**
     * Get object for processing
     *
     * @param context the values for object processing
     */
    @Override
    @SuppressWarnings("unchecked")
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        if (null != context.getObject()) {
            return;
        }
        context.setObject(getObject(context));
    }

    /**
     * {@inheritDoc}
     */
    protected void validateContext(@NotNull final ProcessorContext context) {
        super.validateContext(context);
        if (null == context.getEntity()) {
            throw new RuntimeException();
        }
    }

    /**
     * Get object for processing
     *
     * @param context the values for object processing
     */
    private Object getObject(@NotNull final ProcessorContext context) {
        Object object;
        if (null == context.getObjectId()) {
            return getObjectInstance(context);
        } else {
            return processingService.getObjectDAO().get(context.getEntity(), context.getObjectId());
        }
    }

    /**
     * Create object instance
     *
     * @param context the values for object processing
     */
    private Object getObjectInstance(@NotNull final ProcessorContext context) {
        try {
            return context.getEntity().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
