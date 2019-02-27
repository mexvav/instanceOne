package core.entity.building.builders;

import core.entity.building.Builder;
import core.entity.building.BuildingService;

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
