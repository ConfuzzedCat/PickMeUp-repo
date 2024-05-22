package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.RideRequestID;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RideRequestDAOTest {

    private static Javalin app;
    private static EntityManagerFactory emfTest;
    private static RideRequestDAO dao;
    private Route ride1, ride2, ride3;
    private UserMock passenger, driver;
    private RideRequest r1, r2, r3;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        dao = RideRequestDAO.getInstance(emfTest);
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
            // Reset sequence
            //em.createNativeQuery("ALTER SEQUENCE id RESTART WITH 1").executeUpdate();
            // Insert test data
            passenger = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");
            driver = new UserMock("driver@driversen", "driver123", "Driver", "Driversen");
            em.persist(passenger);
            em.persist(driver);
            ride1 = new Route(2200, 1172, "Rovsingsgade 31", "Nørregade 10", driver.getId(), 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            ride2 = new Route(2000, 1172, "Duevej 92", "Nørregade 10", driver.getId(), 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            ride3 = new Route(2000, 1172, "Frederiksvej 10", "Nørregade 10", driver.getId(), 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            Route[] routeArray = {ride1, ride2, ride3};
            for(Route r: routeArray){
                em.persist(r);
                driver.addRide(r);
            }

            r1 = new RideRequest(passenger, driver, ride1);
            r2 = new RideRequest(passenger, driver, ride2);
            r3 = new RideRequest(passenger, driver, ride3);

            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
        }
    }


    @AfterAll
    static void tearDown() {

        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void readByIDTest() throws ApiException {
        RideRequest found = dao.readByID(1, 3);
        assertEquals("Test", found.getRequestSender().getFirstName());
        assertEquals("Frederiksvej 10", found.getRide().getStartLocation());

        assertThrows(ApiException.class, () -> dao.readByID(2, 2));
    }

    @Test
    void readAllTest() throws ApiException{
        List<RideRequest> allFound = dao.readAll();

        assertEquals(3, allFound.size());
    }

    @Test
    void createTest() throws ApiException{
        RideRequest rideRequest = dao.create(new RideRequest(driver, driver, ride2));
        assertEquals(new RideRequestID(2, 2), rideRequest.getId());

        assertThrows(ApiException.class, () -> dao.create(new RideRequest(passenger, driver, ride1)));
    }

    @Test
    void getRideRequestsForUserTest() throws ApiException{
        List<RideRequest> rideRequests = dao.getIncomingRideRequestsForUser(1);
        assertEquals(3, rideRequests.size());
    }
}
