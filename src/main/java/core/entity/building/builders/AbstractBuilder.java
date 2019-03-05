package core.entity.building.builders;

import core.entity.building.Builder;
import core.entity.building.BuildingService;

public abstract class AbstractBuilder<B> implements Builder<B> {

    private BuildingService buildingService;

    private Class<B> suitableClass;

    AbstractBuilder(BuildingService buildingService, Class<B> suitableClass) {
        this.buildingService = buildingService;
        this.suitableClass = suitableClass;

        buildingService.register(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<B> getSuitableClass() {
        return suitableClass;
    }

    protected BuildingService getBuildingService() {
        return buildingService;
    }
}
