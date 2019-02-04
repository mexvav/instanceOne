package core.jpa.entity.building.builders;

import core.jpa.entity.building.BuildingService;
import javassist.CtClass;

import javax.annotation.Nullable;

public interface Builder<B> {

    /**
     * Initialize buider in building service
     * @param buildingService building service for initialize
     */
    void init(BuildingService buildingService);

    /**
     * Get suitable class for this builder
     */
    Class getSuitableClass();

    /**
     * Build in entity
     *
     * @param ctClass entity class blank
     * @param buildObject object for building
     * @throws core.jpa.entity.building.BuildingException if building is failed
     */
    CtClass build(@Nullable final CtClass ctClass, B buildObject);
}