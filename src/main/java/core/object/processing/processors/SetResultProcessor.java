package core.object.processing.processors;

import core.Constants;
import core.entity.field.EntityField;
import core.interfaces.HasId;
import core.object.processing.ProcessorContext;
import core.object.processing.ResultObject;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class SetResultProcessor extends AbstractProcessor {

    @Override
    public String getProcessCode() {
        return Constants.Processing.SET_RESULT;
    }

    /**
     * Create special object for returning values of processed object
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull final ProcessorContext context) {
        validateContext(context);

        final ResultObject resultObject = new ResultObject();
        resultObject.setId(((HasId)context.getObject()).getId());
        resultObject.setEntityCode(context.getEntityClass().getCode());
        context.getEntityClass().getFields().forEach(entityField -> setValue(context, entityField, resultObject));

        context.setResult(resultObject);
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

    /**
     * Set value for {@link ResultObject} from object field
     *
     * @param context      the values for object processing
     * @param entityField  the description of field in Entity Class
     * @param resultObject the object for returning values of processed object
     */
    private void setValue(@NotNull final ProcessorContext context, @NotNull final EntityField entityField, @NotNull final ResultObject resultObject) {
        try {
            String code = entityField.getCode();
            Field field = context.getObject().getClass().getDeclaredField(code);
            field.setAccessible(true);

            Object value = field.get(context.getObject());
            resultObject.getValues().put(code, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
