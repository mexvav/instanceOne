package core.object.searching.operation;

import core.Constants;

public class GreaterOperation implements SearchOperation {

    private Comparable value;

    public GreaterOperation(Comparable value) {
        this.value = value;
    }

    public Comparable getValue() {
        return value;
    }

    @Override
    public String getCode() {
        return Constants.SearchingService.GREATER_OPERATION;
    }
}
