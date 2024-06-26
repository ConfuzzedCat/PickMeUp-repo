package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Driver;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.Javalin;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import dk.lyngby.model.Route;

import java.time.LocalDateTime;



class RouteDaoTest {
    private static EntityManagerFactory emfTest;

    private static RouteDAO rDao;
    private static Route r1, r2, r3;
    private static Driver d1;
    private static Javalin app;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        rDao = RouteDAO.getInstance(emfTest);
        ApplicationConfig.startServer(app, 7777);

    }


    @BeforeEach
    void setUp() {
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createNativeQuery("TRUNCATE TABLE public.ride_request RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock_route RESTART IDENTITY CASCADE").executeUpdate();
            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.driver RESTART IDENTITY CASCADE").executeUpdate();
            UserMock user = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");

            // Insert test data
            d1 = new Driver(user, "LN123456");
            r1 = new Route(d1, 1, 2,"Start1", "End1", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            r2 = new Route(d1, 2, 1,"Start2", "End2", 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            r3 = new Route(d1, 1, 2, "Start3", "End3", 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            em.persist(d1);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.persist(new Route(d1,3450, 2300, "Byagervej 5", "Studievej 2", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,3450, 2300, "uddrupvej 10", "Studievej 2",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,3600, 2300, "Emdrupvej 128", "Studievej 2",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,3600, 2300, "Hvidovrevej 128", "Studievej 2",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,3600, 2300, "Rødovrevej 213", "Studievej 2",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,2200, 2100, "Madspetersvej 12", "Firskovvej 18",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,2200, 2100, "Galevej 102", "Firskovvej 18",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,2550, 2100, "Hvidkildevej 12", "Firskovvej 18",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,2300, 2100, "Jagtvej 3", "Firskovvej 18",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));
            em.persist(new Route(d1,2800, 2100, "Rovsingsgade 15", "Firskovvej 18",  10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0)));


            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {

        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void getPassengerRoutesWithFilter() {
        RouteDAO routeDao = RouteDAO.getInstance(emfTest);
        List<Route> routeList = routeDao.getPassengerRoutesWithFilter("Studievej,2", 2300, 3450);
        List<Route> routeList1 = routeDao.getPassengerRoutesWithFilter("Firskovvej,18", 2100, 2200);

        assertEquals(2, routeList.size());
        assertEquals(2, routeList1.size());

        assertEquals("Byagervej 5", routeList.get(0).getStartLocation());
        assertEquals("uddrupvej 10", routeList.get(1).getStartLocation());

        assertEquals("Madspetersvej 12", routeList1.get(0).getStartLocation());
        assertEquals("Galevej 102", routeList1.get(1).getStartLocation());

    }

    @Test
    void searchFilters() {
        try {
            var em = emfTest.createEntityManager();
            em.getTransaction().begin();
            var filters = new JsonObject();
            filters.addProperty("startLocation", "Start1");
            var result = rDao.searchFilters(filters);
            assertEquals(1, result.size());
            assertEquals(r1, result.get(0));
            em.getTransaction().commit();
        } catch (Exception e) {
            fail(e);
        }
    }


}

