package core.aspects;

import core.entity.EntityService;
import core.entity.EntityServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Aspect for annotation {@link WithReloadSessionFactory}
 * <p>
 * Wrapping method with annotation in {@link EntityService#actionWithReloadSessionFactory(Supplier)}
 */
@Aspect
@Component
@SuppressWarnings("unused")
public class WithReloadSessionFactoryAspect {

    private EntityService entityService;
    private volatile boolean inReload = false;

    @Autowired
    WithReloadSessionFactoryAspect(EntityService entityService) {
        this.entityService = entityService;
    }

    @Around("@annotation(core.aspects.WithReloadSessionFactory)")
    public synchronized Object withReloadSessionFactory(final ProceedingJoinPoint joinPoint) {
        if (inReload) {
            return action(joinPoint);
        }
        try {
            inReload = true;
            return actionWithReloadSessionFactory(joinPoint);
        } finally {
            inReload = false;
        }
    }

    /**
     * Make some action
     *
     * @param joinPoint join point
     */
    private Object action(final ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new EntityServiceException(e);
        }
    }

    /**
     * Make some action with reload session factory
     *
     * @param joinPoint join point
     */
    private Object actionWithReloadSessionFactory(final ProceedingJoinPoint joinPoint) {
        if (null == entityService) {
            throw new EntityServiceException(EntityServiceException.ExceptionCauses.ENTITY_SERVICE_NOT_EXIST);
        }
        return entityService.actionWithReloadSessionFactory(() -> action(joinPoint));
    }
}