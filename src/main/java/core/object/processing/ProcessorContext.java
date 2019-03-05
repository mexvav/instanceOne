package core.object.processing;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.entities.Entity;

import java.util.List;
import java.util.Map;

/**
 * Context for processing
 */
public class ProcessorContext {

    private Process process;

    private String entityCode;
    private EntityClass entityClass;
    private Class<? extends Entity> entity;

    private Long objectId;
    private Entity object;
    private List<? extends Entity> objects;

    private Map<String, Object> params;
    private Map<String, Object> values;

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
     * Get entity unique identifier
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Set entity unique identifier
     */
    public ProcessorContext setEntityCode(String entityCode) {
        this.entityCode = entityCode;
        return this;
    }

    /**
     * Get {@link EntityClass}
     */
    public EntityClass getEntityClass() {
        return entityClass;
    }

    /**
     * Set {@link EntityClass}
     */
    public ProcessorContext setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    /**
     * Get Entity for object processing
     */
    public Class<? extends Entity> getEntity() {
        return entity;
    }

    /**
     * Set Entity for object processing
     */
    public ProcessorContext setEntity(Class<? extends Entity> entity) {
        this.entity = entity;
        return this;
    }

    /**
     * Get processing object
     */
    public Entity getObject() {
        return object;
    }


    /**
     * Set processing object
     */
    public ProcessorContext setObject(Entity object) {
        this.object = object;
        return this;
    }

    /**
     * Get processing objects
     */
    public List<? extends Entity> getObjects() {
        return objects;
    }

    /**
     * Set processing objects
     */
    public ProcessorContext setObjects(List<? extends Entity> objects) {
        this.objects = objects;
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
