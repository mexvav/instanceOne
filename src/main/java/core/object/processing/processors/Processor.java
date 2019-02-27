package core.object.processing.processors;

import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.utils.suitable.SuitableObjectByCode;

import javax.validation.constraints.NotNull;

/**
 * Part of processing object
 */
public interface Processor extends SuitableObjectByCode<ProcessorContext, ProcessingService> {

    /**
     * Initialize current processor in {@link ProcessingService}
     *
     * @param service service for processing
     */
    void init(ProcessingService service);

    /**
     * Get unique process code
     */
    String getProcessCode();

    /**
     * Make part of processing
     *
     * @param context the values for object processing
     */
    void process(@NotNull final ProcessorContext context);
}
