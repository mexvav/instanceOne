package core.object.validation;

import core.object.validation.validators.Validator;
import core.utils.suitable.AbstractHasSuitableObjectsByClass;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class ValidationService extends AbstractHasSuitableObjectsByClass<Validator> {

    public ValidationService(){
        initializeSuitableObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPackage() {
        return getObjectClass().getPackage().getName();
    }

    @Override
    protected Class<Validator> getObjectClass() {
        return Validator.class;
    }

    @SuppressWarnings("unchecked")
    protected Consumer<Class<? extends Validator>> getSuitableObjectClassConsumer() {
        return (validatorClass) -> {
            try {
                Validator validator = validatorClass.newInstance();
                validator.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ValidationException(e);
            }
        };
    }

    /**
     * Get suitable validators
     *
     * @param object object
     * @return SuitableClassObject
     */
    @SuppressWarnings("unchecked")
    public Set<Validator> getValidators(final Object object) {
        return getSuitableClassObjects().stream().filter(validator ->
                validator.getSuitableClass().isAssignableFrom(object.getClass())
                        && validator.isSuitable(object))
                .collect(Collectors.toSet());
    }
}