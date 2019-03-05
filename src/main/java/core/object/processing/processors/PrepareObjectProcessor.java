package core.object.processing.processors;

import core.Constants;
import core.object.ObjectServiceException;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Map;

@SuppressWarnings("unused")
@Component
public class PrepareObjectProcessor extends AbstractProcessor {

    @Autowired
    PrepareObjectProcessor(ProcessingService processingService) {
        super(processingService, Constants.Processing.PREPARE_OBJECT);
    }

    /**
     * Prepare object for saving, filled values
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        setValues(context, context.getObject());
    }

    /**
     * {@inheritDoc}
     */
    protected void validateContext(@NotNull final ProcessorContext context) {
        super.validateContext(context);
        if (null == context.getObject()) {
            throw new RuntimeException();
        }
    }

    /**
     * Set values for object instance
     *
     * @param context the values for object processing
     */
    private void setValues(@NotNull final ProcessorContext context, @NotNull final Object entityInstance) {
        Map<String, Object> values = context.getValues();
        Class entityClass = entityInstance.getClass();
        for (Map.Entry<String, Object> param : values.entrySet()) {
            try {
                Field field = entityClass.getDeclaredField(param.getKey());
                field.setAccessible(true);
                field.set(entityInstance, param.getValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ObjectServiceException(e);
            }
        }
    }
}
