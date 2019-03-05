package core.object.processing.processors;

import core.Constants;
import core.dao.ObjectDAO;
import core.entity.entities.Entity;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Component
public class GetObjectProcessor extends AbstractProcessor {

    private ObjectDAO objectDAO;

    @Autowired
    GetObjectProcessor(ProcessingService processingService, ObjectDAO objectDAO) {
        super(processingService, Constants.Processing.GET_OBJECT);
        this.objectDAO = objectDAO;
    }

    /**
     * Get object for processing
     *
     * @param context the values for object processing
     */
    @Override
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
    @SuppressWarnings("unchecked")
    private Entity getObject(@NotNull final ProcessorContext context) {
        Object object;
        if (null == context.getObjectId()) {
            return getObjectInstance(context);
        } else {
            return objectDAO.get(context.getEntity(), context.getObjectId());
        }
    }

    /**
     * Create object instance
     *
     * @param context the values for object processing
     */
    private Entity getObjectInstance(@NotNull final ProcessorContext context) {
        try {
            return context.getEntity().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
