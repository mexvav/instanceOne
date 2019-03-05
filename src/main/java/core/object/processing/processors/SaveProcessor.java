package core.object.processing.processors;

import core.Constants;
import core.dao.ObjectDAO;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Component
public class SaveProcessor extends AbstractProcessor {

    private ObjectDAO objectDAO;

    @Autowired
    SaveProcessor(ProcessingService processingService, ObjectDAO objectDAO) {
        super(processingService, Constants.Processing.SAVE);
        this.objectDAO = objectDAO;
    }

    /**
     * Save processed object
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        objectDAO.save(context.getObject());
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
