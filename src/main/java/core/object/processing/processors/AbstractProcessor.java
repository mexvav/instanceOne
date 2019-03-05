package core.object.processing.processors;

import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;

public abstract class AbstractProcessor implements Processor {

    private ProcessingService processingService;

    private String process;

    AbstractProcessor(ProcessingService processingService, String process) {
        this.processingService = processingService;
        setCode(process);
        processingService.register(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return process;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCode(String code) {
        this.process = code;
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

    protected ProcessingService getProcessingService() {
        return processingService;
    }
}
