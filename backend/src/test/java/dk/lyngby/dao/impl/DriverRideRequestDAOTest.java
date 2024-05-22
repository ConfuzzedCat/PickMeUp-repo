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

import static org.junit.jupiter.api.Assertions.*;

public class DriverRideRequestDAOTest {

    private static Javalin app;
    private static EntityManagerFactory emfTest;
    private static DriverRideRequestDAO dao;
    private Route ride1, ride2;
    private UserMock passenger, driver;
    private RideRequest r1, r2;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        dao = DriverRideRequestDAO.getInstance(emfTest);
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp() {
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE public.ride_request RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock_route RESTART IDENTITY CASCADE").executeUpdate();

            passenger = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");
            driver = new UserMock("driver@driversen", "driver123", "Driver", "Driversen");
            em.persist(passenger);
            em.persist(driver);
            ride1 = new Route(2200, 1172, "Rovsingsgade 31", "Nørregade 10", driver.getId(), 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            ride2 = new Route(2000, 1172, "Duevej 92", "Nørregade 10", driver.getId(), 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            em.persist(ride1);
            em.persist(ride2);

            r1 = new RideRequest(passenger, driver, ride1);
            r2 = new RideRequest(passenger, driver, ride2);
            em.persist(r1);
            em.persist(r2);

            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void acceptRideRequest_success() throws ApiException {
        dao.acceptRideRequest(passenger.getId(), ride1.getId());

        try (var em = emfTest.createEntityManager()) {
            RideRequest updatedRequest = em.find(RideRequest.class, new RideRequestID(passenger.getId(), ride1.getId()));
            Route updatedRoute = em.find(Route.class, ride1.getId());

            assertTrue(updatedRequest.isAccepted());
            assertEquals(4, updatedRoute.getPassengerAmount());
        }
    }

    @Test
    void acceptRideRequest_noSeatsAvailable() {
        ride2.setPassengerAmount(3);  // Set passenger amount to maximum

        ApiException exception = assertThrows(ApiException.class, () -> {
            dao.acceptRideRequest(passenger.getId(), ride2.getId());
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("No available seats in this ride", exception.getMessage());
    }

    @Test
    void declineRideRequest_success() throws ApiException {
        dao.declineRideRequest(passenger.getId(), ride1.getId());

        try (var em = emfTest.createEntityManager()) {
            RideRequest deletedRequest = em.find(RideRequest.class, new RideRequestID(passenger.getId(), ride1.getId()));
            assertNull(deletedRequest);
        }
    }

    @Test
    void declineRideRequest_notFound() {
        ApiException exception = assertThrows(ApiException.class, () -> {
            dao.declineRideRequest(999, 999);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("Ride request not found", exception.getMessage());
    }
}
