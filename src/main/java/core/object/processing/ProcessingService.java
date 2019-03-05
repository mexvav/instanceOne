package core.object.processing;

import core.object.processing.processors.Processor;
import core.utils.register.AbstractRegisteringServiceByCode;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class ProcessingService extends AbstractRegisteringServiceByCode<Processor> {

    /**
     * Processing object by {@link ProcessorContext}
     *
     * @param context the values for object processing
     */
    public ProcessorContext processing(@NotNull final ProcessorContext context) {
        if (null == context) {
            throw new RuntimeException();
        }
        Process process = context.getProcess();
        if (null == process) {
            throw new RuntimeException();
        }
        Arrays.asList(process.getProcessors()).forEach(processorCode -> {
            Processor processor = get(processorCode);
            processor.process(context);
        });
        return context;
    }
}
