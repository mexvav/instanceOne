package test_core.assertions;

import test_core.utils.SpringContextUtils;

public class SpringContextAssertions {

    private EntityAssertions entityAssertions;
    private ObjectAssertions objectAssertions;

    public SpringContextAssertions(SpringContextUtils utils){
        entityAssertions = new EntityAssertions(utils);
        objectAssertions = new ObjectAssertions(utils);
    }

    public EntityAssertions entity(){
        return entityAssertions;
    }

    public ObjectAssertions object(){
        return objectAssertions;
    }
}
