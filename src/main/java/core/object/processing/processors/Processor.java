package core.object.processing.processors;

import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.utils.register.RegisteredObjectWithCode;

import javax.validation.constraints.NotNull;

/**
 * Part of processing object
 */
public interface Processor extends RegisteredObjectWithCode<ProcessorContext, ProcessingService> {

    /**
     * Make part of processing
     *
     * @param context the values for object processing
     */
    void process(@NotNull final ProcessorContext context);
}
