package spring_context_tests;

import core.jpa.dao.EntityDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

class EntityDAOTest extends SpringContextAbstractTest {

    @Autowired
    EntityDAO dao;

    @Test
    void testTable() {
        Set<String> tables = dao.getAllTables();
    }
}
