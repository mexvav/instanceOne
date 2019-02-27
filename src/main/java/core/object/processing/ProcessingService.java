package core.object.processing;

import core.dao.ObjectDAO;
import core.entity.EntityService;
import core.mapping.MappingException;
import core.object.processing.processors.Processor;
import core.object.resolving.ResolvingService;
import core.object.validation.ValidationService;
import core.utils.suitable.AbstractHasSuitableObjectsByCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.function.Consumer;

@Component
public class ProcessingService extends AbstractHasSuitableObjectsByCode<Processor> {

    private ResolvingService resolvingService;
    private ValidationService validationService;
    private EntityService entityService;
    private ObjectDAO objectDAO;

    @Autowired
    public ProcessingService(ResolvingService resolvingService,
                             ValidationService validationService,
                             EntityService entityService,
                             ObjectDAO objectDAO) {
        this.resolvingService = resolvingService;
        this.validationService = validationService;
        this.entityService = entityService;
        this.objectDAO = objectDAO;
        initializeSuitableObjects();
    }

    /**
     * Processing object by {@link ProcessorContext}
     *
     * @param context the values for object processing
     */
    public ResultObject processing(@NotNull final ProcessorContext context) {
        if (null == context) {
            throw new RuntimeException();
        }
        Process process = context.getProcess();
        if (null == process) {
            throw new RuntimeException();
        }
        Arrays.asList(process.getProcessors()).forEach(processorCode -> {
            Processor processor = getSuitableObject(processorCode);
            processor.process(context);
        });
        return context.getResult();
    }

    public ResolvingService getResolvingService() {
        return resolvingService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public EntityService getEntityService() {
        return entityService;
    }

    public ObjectDAO getObjectDAO() {
        return objectDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPackage() {
        return getObjectClass().getPackage().getName();
    }

    /**
     * {@inheritDoc}
     */
    protected Class<Processor> getObjectClass() {
        return Processor.class;
    }

    /**
     * {@inheritDoc}
     */
    protected Consumer<Class<? extends Processor>> getSuitableObjectClassConsumer() {
        return (processorClass) -> {
            try {
                Processor processor = processorClass.newInstance();
                processor.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new MappingException(e);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initSuitableObject(Processor processor) {
        getSuitableObjects().put(processor.getProcessCode(), processor);
    }
}
