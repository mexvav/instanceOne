package core.object;

import core.object.processing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ObjectService {

    private ProcessingService processingService;

    @Autowired
    ObjectService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    /**
     * Create object
     *
     * @param entityCode the code of entity
     * @param params     the values for object
     */
    public DataObject create(String entityCode, Map<String, Object> params) {
        ProcessorContext context = ContextFactory.create(entityCode, params);
        return new DataObjectWrapper(processingService.processing(context).getObject());
    }

    /**
     * Get object
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     */
    public DataObject get(String entityCode, long objectId) {
        ProcessorContext context = ContextFactory.get(entityCode, objectId);
        return new DataObjectWrapper(processingService.processing(context).getObject());
    }

    /**
     * Edit object
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     * @param params     the values for object
     */
    public DataObject edit(String entityCode, long objectId, Map<String, Object> params) {
        ProcessorContext context = ContextFactory.edit(entityCode, objectId, params);
        return new DataObjectWrapper(processingService.processing(context).getObject());
    }

    /**
     * Edit object
     *
     * @param object the editing object
     * @param params the values for object
     */
    public DataObject edit(DataObject object, Map<String, Object> params) {
        return edit(object.getEntityCode(), object.getId(), params);
    }

    /**
     * Remove object
     *
     * @param object the editing object
     */
    public void remove(DataObject object) {
        ProcessorContext context = ContextFactory.remove(object);
        processingService.processing(context);
    }

    /**
     * Remove object
     *
     * @param entityCode the code of entity
     * @param objectId   the unique id
     */
    public void remove(String entityCode, long objectId) {
        ProcessorContext context = ContextFactory.remove(entityCode, objectId);
        processingService.processing(context);
    }

    /**
     * Search objects
     *
     * @param entityCode the code of entity
     * @param params     the values for object
     */
    public List<DataObject> search(String entityCode, Map<String, Object> params) {
        ProcessorContext context = ContextFactory.search(entityCode, params);
        return processingService.processing(context).getObjects()
                .stream().map(DataObjectWrapper::new).collect(Collectors.toList());
    }
}
