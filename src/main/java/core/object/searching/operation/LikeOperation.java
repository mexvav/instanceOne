package core.object.searching.operation;

import core.Constants;

public class LikeOperation implements SearchOperation {

    private String value;

    public LikeOperation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getCode() {
        return Constants.SearchingService.LIKE_OPERATION;
    }
}
