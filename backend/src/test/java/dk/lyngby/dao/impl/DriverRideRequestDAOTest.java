package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.*;
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
    private UserMock passenger;
    private Driver driver;

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
            em.createNativeQuery("TRUNCATE TABLE public.driver RESTART IDENTITY CASCADE").executeUpdate();

            passenger = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");
            UserMock user = new UserMock("driver@driversen", "driver123", "Driver", "Driversen");
            driver = new Driver(user, "LN123");
            em.persist(passenger);
            em.persist(user);  // Persist user here
            em.persist(driver);

            ride1 = new Route(driver, 2200, 1172, "Rovsingsgade 31", "Nørregade 10", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            ride2 = new Route(driver, 2000, 1172, "Duevej 92", "Nørregade 10", 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            em.persist(ride1);
            em.persist(ride2);

            r1 = new RideRequest(passenger, user, ride1);
            r2 = new RideRequest(passenger, user, ride2);
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
    void declineRideRequest_success() throws ApiException {
        dao.declineRideRequest(passenger.getId(), ride1.getId());

        try (var em = emfTest.createEntityManager()) {
            RideRequest deletedRequest = em.find(RideRequest.class, new RideRequestID(passenger.getId(), ride1.getId()));
            assertNull(deletedRequest);
        }
    }
}

