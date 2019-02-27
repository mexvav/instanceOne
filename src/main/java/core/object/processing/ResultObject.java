package core.object.processing;

import com.google.common.collect.Maps;
import core.Constants;
import core.interfaces.HasEntityCode;
import core.interfaces.HasId;

import java.util.Map;

public class ResultObject implements HasId, HasEntityCode {

    private Map<String, Object> values;

    @Override
    public String getEntityCode() {
        return (String) getValue(Constants.HasEntityCode.ENTITY_CODE);
    }

    public void setEntityCode(String code) {
        setValue(Constants.HasEntityCode.ENTITY_CODE, code);
    }

    @Override
    public Long getId() {
        return (Long) getValue(Constants.HasId.ID);
    }

    @Override
    public void setId(Long id) {
        setValue(Constants.HasId.ID, id);
    }

    public Map<String, Object> getValues() {
        if (null == values) {
            values = Maps.newHashMap();
        }
        return values;
    }

    public Object getValue(String code) {
        return getValues().get(code);
    }

    public void setValue(String code, Object value) {
        getValues().put(code, value);
    }
}
