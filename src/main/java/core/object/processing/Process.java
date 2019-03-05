package core.object.processing;

import static core.Constants.Processing.*;

/**
 * Description of processes
 */
public enum Process {

    /**
     * Creating object process
     */
    CREATE(new String[]{
            GET_ENTITY,
            VALIDATION_PARAMS,
            GET_OBJECT,
            RESOLVING_VALUES,
            PREPARE_OBJECT,
            SAVE
    }),

    /**
     * Get object
     */
    GET(new String[]{
            GET_ENTITY,
            GET_OBJECT
    }),

    /**
     * Edit object process
     */
    EDIT(new String[]{
            GET_ENTITY,
            VALIDATION_PARAMS,
            GET_OBJECT,
            RESOLVING_VALUES,
            PREPARE_OBJECT,
            SAVE
    }),

    /**
     * Remove object process
     */
    REMOVE(new String[]{
            GET_ENTITY,
            GET_OBJECT,
            REMOVE_OBJECT
    }),

    /**
     * Searching objects process
     */
    SEARCH(new String[]{
            GET_ENTITY,
            RESOLVING_VALUES,
            SEARCH_OBJECTS
    });

    String[] processors;

    Process(String[] processors) {
        this.processors = processors;
    }

    public String[] getProcessors() {
        return processors;
    }
}
