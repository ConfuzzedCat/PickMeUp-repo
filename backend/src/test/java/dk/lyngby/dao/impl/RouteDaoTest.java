package dk.lyngby.dao.impl;

import com.google.gson.JsonObject;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RouteDaoTest {

    private static EntityManagerFactory emfTest;
    private static EntityManager em;

    private static RouteDao rDao;
    private static Route r1, r2, r3;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        rDao = new RouteDao();
        RouteDao.getInstance(emfTest);
    }

    @BeforeEach
    void setUp(){
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Route r").executeUpdate();
            // Reset sequence
            //em.createNativeQuery("ALTER SEQUENCE id RESTART WITH 1").executeUpdate();
            // Insert test data
            r1 = new Route("Start1", "End1", 1, 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            r2 = new Route("Start2", "End2", 2, 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            r3 = new Route("Start3", "End3", 3, 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
        }

    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
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