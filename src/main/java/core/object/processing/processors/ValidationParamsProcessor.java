package core.object.processing.processors;

import core.Constants;
import core.entity.EntityService;
import core.object.ObjectServiceException;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Component
public class ValidationParamsProcessor extends AbstractProcessor {

    private EntityService entityService;

    ValidationParamsProcessor(ProcessingService processingService) {
        super(processingService, Constants.Processing.VALIDATION_PARAMS);
    }

    /**
     * Validate params for processing
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        context.getParams().keySet().forEach(field -> checkEntityContainsField(context, field));
    }

    /**
     * @throws ObjectServiceException is entity not contains field
     */
    private void checkEntityContainsField(ProcessorContext context, String fieldCode) {
        if (!context.getEntityClass().getFieldCodes().contains(fieldCode)) {
            throw new ObjectServiceException(ObjectServiceException.ExceptionCauses.FIELD_IS_NOT_EXIST, fieldCode);
        }
    }
}
