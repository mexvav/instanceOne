package core.jpa.entity.building.builders;

import core.jpa.entity.building.BuildingService;

public abstract class AbstractBuilder<B> implements Builder<B> {

    BuildingService buildingService;

    @Override
    public void init(BuildingService buildingService) {
        this.buildingService = buildingService;
        buildingService.initSuitableClassObject(this);
    }
}
