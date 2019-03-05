package core.object.processing;

import java.util.Map;

public class ContextFactory {

    /**
     * Get context for object creating
     *
     * @param entityCode the code of entity
     * @param params     the values for object
     */
    public static ProcessorContext create(String entityCode, Map<String, Object> params) {
        return new ProcessorContext()
                .setEntityCode(entityCode)
                .setParams(params)
                .setProcess(Process.CREATE);
    }

    /**
     * Get context for getting object
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     */
    public static ProcessorContext get(String entityCode, long objectId) {
        return new ProcessorContext()
                .setEntityCode(entityCode)
                .setObjectId(objectId)
                .setProcess(Process.GET);
    }

    /**
     * Get context for object editing
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     * @param params     the values for object
     */
    public static ProcessorContext edit(String entityCode, long objectId, Map<String, Object> params) {
        return new ProcessorContext()
                .setEntityCode(entityCode)
                .setObjectId(objectId)
                .setParams(params)
                .setProcess(Process.EDIT);
    }

    /**
     * Get context for editing object
     *
     * @param object the editing object
     * @param params the values for object
     */
    public static ProcessorContext edit(DataObject object, Map<String, Object> params) {
        return edit(object.getEntityCode(), object.getId(), params);
    }

    /**
     * Get context for removing object
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     */
    public static ProcessorContext remove(String entityCode, long objectId) {
        return new ProcessorContext()
                .setEntityCode(entityCode)
                .setObjectId(objectId)
                .setProcess(Process.REMOVE);
    }

    /**
     * Get context for removing object
     *
     * @param object the editing object
     */
    public static ProcessorContext remove(DataObject object) {
        return remove(object.getEntityCode(), object.getId());
    }

    /**
     * Get context for removing object
     *
     * @param entityCode the code of entity
     * @param params     the values for object
     */
    public static ProcessorContext search(String entityCode, Map<String, Object> params) {
        return new ProcessorContext()
                .setEntityCode(entityCode)
                .setParams(params)
                .setProcess(Process.SEARCH);
    }
}
