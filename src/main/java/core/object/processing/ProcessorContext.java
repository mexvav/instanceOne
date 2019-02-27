package core.object.processing;

import com.google.common.collect.Maps;
import core.entity.EntityClass;

import java.util.Map;

public class ProcessorContext {

    private Process process;

    private String entityCode;
    private EntityClass entityClass;
    private Class entity;

    private Long objectId;
    private Object object;

    private Map<String, Object> params;
    private Map<String, Object> values;

    private ResultObject result;

    public ProcessorContext() {
    }

    /**
     * Get current process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * Set current process
     */
    public ProcessorContext setProcess(Process process) {
        this.process = process;
        return this;
    }

    /**
     * Get code for object entity
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Set code for object entity
     */
    public ProcessorContext setEntityCode(String entityCode) {
        this.entityCode = entityCode;
        return this;
    }

    /**
     * Get {@link EntityClass} for object
     */
    public EntityClass getEntityClass() {
        return entityClass;
    }

    /**
     * Set {@link EntityClass} for object
     */
    public ProcessorContext setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    /**
     * Get Entity for object processing
     */
    public Class getEntity() {
        return entity;
    }

    /**
     * Set Entity for object processing
     */
    public ProcessorContext setEntity(Class entity) {
        this.entity = entity;
        return this;
    }

    /**
     * Get processing object
     */
    public Object getObject() {
        return object;
    }


    /**
     * Set processing object
     */
    public ProcessorContext setObject(Object object) {
        this.object = object;
        return this;
    }

    /**
     * Get raw values for processing object
     */
    public Map<String, Object> getParams() {
        if (null == params) {
            params = Maps.newHashMap();
        }
        return params;
    }

    /**
     * Set raw values for processing object
     */
    public ProcessorContext setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    /**
     * Get values for processing object
     */
    public Map<String, Object> getValues() {
        if (null == values) {
            values = Maps.newHashMap();
        }
        return values;
    }

    /**
     * Set values for processing object
     */
    public ProcessorContext setValues(Map<String, Object> values) {
        this.values = values;
        return this;
    }

    /**
     * Get processing result
     */
    public ResultObject getResult() {
        return result;
    }

    /**
     * Set processing result
     */
    public ProcessorContext setResult(ResultObject result) {
        this.result = result;
        return this;
    }

    /**
     * Get id processing object
     */
    public Long getObjectId() {
        return objectId;
    }

    /**
     * Set id processing object
     */
    public ProcessorContext setObjectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }
}
