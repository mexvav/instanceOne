package core.utils.suitable;


import core.jpa.entity.building.Builder;
import core.utils.ReflectionUtils;

import java.util.function.Consumer;

public abstract class AbstractHasSuitableObjects<R extends SuitableObject> implements HasSuitableObjects<R> {

    /**Package for finding {@link SuitableObject}*/
    protected abstract String getPackage();

    /**Class {@link SuitableObject}*/
    protected abstract Class<R> getSuitableClass();

    /**Initializer {@link SuitableObject}*/
    protected abstract Consumer<Class<? extends R>> getSuitableObjectClassConsumer();

    /**
     * Initialize all {@link SuitableObject} in package {@link #getPackage()}
     */
    protected void initializeSuitableObjects() {
        ReflectionUtils.actionWithSubTypes(getPackage(), getSuitableClass(), getSuitableObjectClassConsumer());
    }
}