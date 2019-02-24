package cases.mock;

import core.jpa.session_factory.ReloadableSessionFactory;
import core.jpa.session_factory.ReloadableSessionFactoryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import java.util.Properties;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReloadableSessionFactoryBuilder.class)
public class ReloadableSessionFactoryTest {


    private static ReloadableSessionFactory reloadableSessionFactory;
    private static Configuration configuration;
    private static SessionFactory sessionFactory;

    @Before
    public void initialize() throws Exception {
        sessionFactory = Mockito.mock(SessionFactory.class);
        configuration = Mockito.mock(Configuration.class);

        PowerMockito.whenNew(Configuration.class).withAnyArguments().thenReturn(configuration);
        Mockito.when(configuration.buildSessionFactory(Mockito.any())).thenReturn(sessionFactory);

        HibernateTransactionManager hibernateTransactionManager = Mockito.mock(HibernateTransactionManager.class);
        PowerMockito.whenNew(HibernateTransactionManager.class).withAnyArguments().thenReturn(hibernateTransactionManager);

        ReloadableSessionFactoryBuilder builder = new ReloadableSessionFactoryBuilder(new Properties(), null);
        reloadableSessionFactory = builder.build();
    }


    @Test
    public void testReloadSessionFactory()  {

        Mockito.verify(configuration, Mockito.times(1)).buildSessionFactory(Mockito.any());
        Assertions.assertTrue(reloadableSessionFactory.getCurrentEntities().isEmpty());

        //@TODO ReloadableSessionFactory.getCurrentEntities() must by unmutable. add methods 'addEntity', 'removeEntity'
        //@TODO 'addEntity' throws if null
        reloadableSessionFactory.getCurrentEntities().add(null);
        Assertions.assertFalse(reloadableSessionFactory.getCurrentEntities().isEmpty());

        reloadableSessionFactory.reloadSessionFactory();
        Mockito.verify(configuration, Mockito.times(2)).buildSessionFactory(Mockito.any());


    }

}
