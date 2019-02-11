package spring_context_tests;


import core.jpa.dao.DAOEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

class DAOEntityTest extends SpringContextAbstractTest {

    @Autowired
    DAOEntity dao;


    @Test
    void testTable() {
        Set<String> tables = dao.getAllTables();

    }
}
