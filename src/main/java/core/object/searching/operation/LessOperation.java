package core.object.searching.operation;

import core.Constants;

public class LessOperation implements SearchOperation {

    private Comparable value;

    public LessOperation(Comparable value) {
        this.value = value;
    }

    public Comparable getValue() {
        return value;
    }

    @Override
    public String getCode() {
        return Constants.SearchingService.LESS_OPERATION;
    }
}
