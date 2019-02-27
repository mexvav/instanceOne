package core.object;

import core.object.processing.Process;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.object.processing.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ObjectService {

    private ProcessingService processingService;

    @Autowired
    ObjectService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    public ResultObject create(String entityCode, Map<String, Object> params) {
        ProcessorContext context = new ProcessorContext()
                .setEntityCode(entityCode)
                .setParams(params)
                .setProcess(Process.CREATE);
        return processingService.processing(context);
    }

    public ResultObject edit(String entityCode, long objectId, Map<String, Object> params) {
        ProcessorContext context = new ProcessorContext()
                .setEntityCode(entityCode)
                .setObjectId(objectId)
                .setParams(params)
                .setProcess(Process.EDIT);
        return processingService.processing(context);
    }

    public ResultObject edit(ResultObject object, Map<String, Object> params) {
        return edit(object.getEntityCode(), object.getId(), params);
    }

    public void remove(Object object) {
        ProcessorContext context = new ProcessorContext()
                .setObject(object)
                .setProcess(Process.REMOVE);
        processingService.processing(context);
    }

    public void remove(String entityCode, long id) {
        ProcessorContext context = new ProcessorContext()
                .setEntityCode(entityCode)
                .setObjectId(id)
                .setProcess(Process.REMOVE);
        processingService.processing(context);
    }

}
