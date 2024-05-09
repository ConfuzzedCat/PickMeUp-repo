package dk.lyngby.dao.impl;

import dk.lyngby.Model.Route;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteDaoTest {


    private static Javalin app;

    private static final String BASE_URL = "http://localhost:7777/api/v1";

    private static EntityManagerFactory emfTest;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);

    }

    @BeforeEach
    void setUp() {
        try(EntityManager emTest = emfTest.createEntityManager()){
            // Insert test rows
            emTest.getTransaction().begin();

            emTest.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY").executeUpdate();


            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('1','Studievej 2','2300','Byagervej 5','3450');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('2','Studievej 2','2300','uddrupvej 10','3450');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('3','Studievej 2','2300','Emdrupvej 128','3600');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('4','Studievej 2','2300','Hvidovrevej 128','3600');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('5','Studievej 2','2300','Rødovrevej 213','3600');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('6','Firskovvej 18','2100','Madspetersvej 12','2200');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('7','Firskovvej 18','2100','Galevej 102','2200');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('8','Firskovvej 18','2100','Hvidkildevej 12','2550');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('9','Firskovvej 18','2100','Jagtvej 3','2300');").executeUpdate();
            emTest.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('10','Firskovvej 18','2100','Rovsingsgade 15','2800');").executeUpdate();

            emTest.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {

        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void getPassengerRoutesWithFilter() {
        RouteDao routeDao = RouteDao.getInstance();
        List<Route> routeList = routeDao.getPassengerRoutesWithFilter("Studievej,2", 2300, 3450);
        List<Route> routeList1 = routeDao.getPassengerRoutesWithFilter("Firskovvej,18", 2100, 2200);

        assertEquals(2, routeList.size());
        assertEquals(2, routeList1.size());

        assertEquals("Byagervej 5", routeList.get(0).getStartLocation());
        assertEquals("uddrupvej 10", routeList.get(1).getStartLocation());

        assertEquals("Madspetersvej 12", routeList1.get(0).getStartLocation());
        assertEquals("Galevej 102", routeList1.get(1).getStartLocation());

    }
}