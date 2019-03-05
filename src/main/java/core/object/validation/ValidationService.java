package core.object.validation;

import core.object.validation.validators.Validator;
import core.utils.register.AbstractRegisteringServiceByClass;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationService extends AbstractRegisteringServiceByClass<Validator> {

    /**
     * Get suitable validators
     *
     * @param object object
     * @return SuitableClassObject
     */
    @SuppressWarnings("unchecked")
    public Set<Validator> getValidators(final Object object) {
        return getRegisteredObjects().stream().filter(validator ->
                validator.getSuitableClass().isAssignableFrom(object.getClass())
                        && validator.isSuitable(object))
                .collect(Collectors.toSet());
    }
}