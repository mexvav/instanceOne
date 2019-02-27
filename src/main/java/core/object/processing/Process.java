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
            GET_OBJECT,
            SET_VALUES,
            PREPARE_OBJECT,
            SAVE,
            SET_RESULT
    }),
    GET(new String[]{
            GET_ENTITY,
            GET_OBJECT,
            SET_RESULT
    }),
    /**
     * Edit object process
     */
    EDIT(new String[]{
            GET_ENTITY,
            GET_OBJECT,
            SET_VALUES,
            PREPARE_OBJECT,
            SAVE,
            SET_RESULT}),
    /**
     * Remove object process
     */
    REMOVE(new String[]{
            GET_ENTITY,
            GET_OBJECT,
            REMOVE_OBJECT
    });

    String[] processors;

    Process(String[] processors) {
        this.processors = processors;
    }

    public String[] getProcessors() {
        return processors;
    }
}
