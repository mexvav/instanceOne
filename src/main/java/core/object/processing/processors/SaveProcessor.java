package core.object.processing.processors;

import core.Constants;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class SaveProcessor extends AbstractProcessor {

    @Override
    public String getProcessCode() {
        return Constants.Processing.SAVE;
    }

    /**
     * Save processed object
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        processingService.getObjectDAO().save(context.getObject());
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
}
