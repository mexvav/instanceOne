package core.object.searching;

import core.object.searching.operation.GreaterOperation;
import core.object.searching.operation.LessOperation;
import core.object.searching.operation.LikeOperation;

public class OperationFactory {

    public static LikeOperation like(String value){
        return new LikeOperation(value);
    }

    public static LessOperation less(Comparable value){
        return new LessOperation(value);
    }

    public static GreaterOperation greater(Comparable value){
        return new GreaterOperation(value);
    }
}
