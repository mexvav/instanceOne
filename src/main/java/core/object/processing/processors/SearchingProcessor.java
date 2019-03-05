package core.object.processing.processors;

import com.google.common.collect.Maps;
import core.Constants;
import core.entity.entities.Entity;
import core.object.processing.ProcessingService;
import core.object.processing.ProcessorContext;
import core.object.searching.SearchingService;
import core.object.searching.operation.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
public class SearchingProcessor extends AbstractProcessor {

    private SearchingService searchingService;

    @Autowired
    SearchingProcessor(ProcessingService processingService, SearchingService searchingService) {
        super(processingService, Constants.Processing.SEARCH_OBJECTS);
        this.searchingService = searchingService;
    }

    /**
     * Searching objects
     *
     * @param context the values for object processing
     */
    @Override
    public void process(@NotNull ProcessorContext context) {
        List<? extends Entity> objects = searchingService.search(context.getEntity(), getSearchingParams(context));
        context.setObjects(objects);
    }

    /**
     * Get parameters for searching
     *
     * @param context the values for object processing
     */
    private Map<String, Object> getSearchingParams(@NotNull ProcessorContext context) {
        Map<String, Object> searchingParams = Maps.newHashMap();
        context.getParams().keySet().forEach(field -> {
            Object value = context.getParams().get(field);
            if (value instanceof SearchOperation) {
                searchingParams.put(field, value);
            } else {
                searchingParams.put(field, context.getValues().get(field));
            }
        });
        return searchingParams;
    }
}
