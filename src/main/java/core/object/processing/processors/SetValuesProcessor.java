package core.object.processing.processors;

import core.Constants;
import core.entity.EntityClass;
import core.entity.field.EntityField;
import core.object.processing.ProcessorContext;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class SetValuesProcessor extends AbstractProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProcessCode() {
        return Constants.Processing.SET_VALUES;
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
        Objects.requireNonNull(entityClass).getFields().forEach(field -> fieldProcess(context, field));
    }

    /**
     * Set value for {@link EntityField} in {@link ProcessorContext#setValues(Map)}
     *
     * @param context the context for processing
     * @param field   the {@link EntityField} of processing object
     */
    @SuppressWarnings("unchecked")
    private void fieldProcess(@NotNull final ProcessorContext context, @NotNull final EntityField field) {
        final Object rawValue = context.getParams().getOrDefault(Objects.requireNonNull(field).getCode(), null);
        final Object value = processingService.getResolvingService().resolve(field, rawValue);
        processingService.getValidationService()
                .getValidators(field).forEach(validator -> validator.validation(field, value));
        context.getValues().put(field.getCode(), value);
    }
}
