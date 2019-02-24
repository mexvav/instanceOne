package core.jpa.entity.building.builders;

import core.jpa.entity.building.Builder;
import core.jpa.entity.building.BuildingService;

public abstract class AbstractBuilder<B> implements Builder<B> {

    BuildingService buildingService;

    /**
     * {@inheritDoc}
     * @param buildingService
     */
    @Override
    public void init(BuildingService buildingService) {
        this.buildingService = buildingService;
        buildingService.initSuitableObject(this);
    }
}