package core.object.searching.operation;

import core.object.searching.SearchingService;

public abstract class AbstractSearchOperationHandler<T extends SearchOperation> implements SearchOperationHandler<T> {

    private String code;

    public AbstractSearchOperationHandler(SearchingService service, String code) {
        setCode(code);
        service.register(this);
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
