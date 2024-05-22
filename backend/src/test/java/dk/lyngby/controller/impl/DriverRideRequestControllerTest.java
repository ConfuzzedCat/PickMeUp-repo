package dk.lyngby.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.impl.DriverRideRequestDAO;
import dk.lyngby.dto.DriverRideRequestDTO;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.RideRequestID;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import dk.lyngby.routes.RequestsRoute;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class DriverRideRequestControllerTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/requests/";
    private static EntityManagerFactory emfTest;
    private static ObjectMapper objectMapper;
    private Route ride1;
    private UserMock passenger, driver;
    private RideRequest r1;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();

        // Register routes directly
        app.routes(() -> {
            new RequestsRoute().getRoutes();
        });

        ApplicationConfig.startServer(app, 7777);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7777;

        // Initialize the ObjectMapper
        objectMapper = new ObjectMapper();
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
            em.persist(ride1);

            r1 = new RideRequest(passenger, driver, ride1);
            em.persist(r1);

            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void acceptRideRequest_success() {
        try {
            DriverRideRequestDTO requestDTO = new DriverRideRequestDTO(passenger.getId(), ride1.getId(), true);
            String jsonRequest = objectMapper.writeValueAsString(requestDTO);
            System.out.println(jsonRequest);

            given().
                    contentType("application/json").
                    body(jsonRequest).
                    when().
                    post(BASE_URL + "accept").
                    then().
                    statusCode(200).
                    body(equalTo("Ride request accepted successfully"));

            try (var em = emfTest.createEntityManager()) {
                RideRequest updatedRequest = em.find(RideRequest.class, new RideRequestID(passenger.getId(), ride1.getId()));
                Route updatedRoute = em.find(Route.class, ride1.getId());

                assertTrue(updatedRequest.isAccepted());
                assertEquals(4, updatedRoute.getPassengerAmount());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void declineRideRequest_success() {
        try {
            DriverRideRequestDTO requestDTO = new DriverRideRequestDTO(passenger.getId(), ride1.getId(), false);
            String jsonRequest = objectMapper.writeValueAsString(requestDTO);

            given().
                    contentType("application/json").
                    body(jsonRequest).
                    when().
                    post(BASE_URL + "decline").
                    then().
                    statusCode(200).
                    body(equalTo("Ride request declined successfully"));

            try (var em = emfTest.createEntityManager()) {
                RideRequest deletedRequest = em.find(RideRequest.class, new RideRequestID(passenger.getId(), ride1.getId()));
                assertNull(deletedRequest);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
