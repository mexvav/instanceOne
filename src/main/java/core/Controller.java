package core;

import com.google.common.collect.Lists;
import core.jpa.EntityRuntime;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/app")
@Transactional
public class Controller {

    @Autowired
    SessionFactory sessionFactory;

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key)
    {
        return "Hello world";
    }

    @GetMapping("create")
    public String create(){
        Class test = null;
        try {
            test = EntityRuntime.getEntityClass();
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }



        try {
            Object object = test.newInstance();
            Field f = test.getDeclaredField("string");
            f.setAccessible(true);
            f.set(object, "test");


            Session session = sessionFactory.getCurrentSession();
            EntityManagerFactory entityManagerFactory1 = session.getEntityManagerFactory();
            Map<String, Object> prop = entityManagerFactory1.getProperties();


            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            Configuration conf = new Configuration();
            conf.addAnnotatedClass(test);


            Properties properties = new Properties();
            properties.put(org.hibernate.jpa.AvailableSettings.LOADED_CLASSES, Lists.newArrayList(test));
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit", properties);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();







            //
            session.save(object);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }


        Hibernate.initialize(test);

        String z = null != test  ? test.getName() : null;
        return z;
    }
}
