package core.jpa.aspects;

import core.jpa.entity.EntityService;
import core.jpa.entity.EntityServiceException;
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

    @Autowired
    WithReloadSessionFactoryAspect(EntityService entityService) {
        this.entityService = entityService;
    }

    @Around("@annotation(core.jpa.aspects.WithReloadSessionFactory)")
    public Object withReloadSessionFactory(ProceedingJoinPoint joinPoint) {
        if (null == entityService) {
            throw new EntityServiceException("There is not exist EntityService component");
        }
        return entityService.actionWithReloadSessionFactory(
                () -> {
                    try {
                        return joinPoint.proceed();
                    } catch (Throwable e) {
                        throw new EntityServiceException(e.getMessage());
                    }
                });
    }
}