package core.object.processing.processors;

import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;

public abstract class AbstractProcessor implements Processor {

    protected ProcessingService processingService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(@NotNull ProcessingService processingService) {
        if (null == processingService) {
            throw new RuntimeException();
        }
        this.processingService = processingService;
        processingService.initSuitableObject(this);
    }

    /**
     * Validate context
     *
     * @param context the values for object processing
     */
    protected void validateContext(@NotNull final ProcessorContext context) {
        if (null == context) {
            throw new RuntimeException();
        }
    }
}
