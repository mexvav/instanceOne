package core.object.processing.processors;

import core.Constants;
import core.entity.EntityClass;
import core.entity.field.EntityField;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.object.resolving.ResolvingService;
import core.object.searching.operation.SearchOperation;
import core.object.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
@Component
public class ResolvingValuesProcessor extends AbstractProcessor {

    private ResolvingService resolvingService;
    private ValidationService validationService;

    @Autowired
    ResolvingValuesProcessor(ProcessingService processingService,
                             ResolvingService resolvingService,
                             ValidationService validationService) {
        super(processingService, Constants.Processing.RESOLVING_VALUES);
        this.resolvingService = resolvingService;
        this.validationService = validationService;
    }

    /**
     * Set all values for {@link EntityField} in {@link ProcessorContext#setValues(Map)}
     *
     * @param context the values for processing object
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);
        EntityClass entityClass = context.getEntityClass();
        Objects.requireNonNull(entityClass).getFields().forEach(field -> fieldValueResolve(context, field));
    }

    /**
     * Resolving value for {@link EntityField}
     *
     * @param context the context for processing
     * @param field   the {@link EntityField}
     */
    @SuppressWarnings("unchecked")
    private void fieldValueResolve(@NotNull final ProcessorContext context, @NotNull final EntityField field) {
        final Object rawValue = context.getParams().getOrDefault(Objects.requireNonNull(field).getCode(), null);
        if (rawValue instanceof SearchOperation) {
            return;
        }
        final Object value = resolvingService.resolve(field, rawValue);
        validationService.getValidators(field).forEach(validator -> validator.validation(field, value));
        context.getValues().put(field.getCode(), value);
    }
}
