package dk.lyngby;
import dk.lyngby.dto.RideRequestDTO;
import dk.lyngby.model.*;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;

import java.time.LocalDateTime;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestTest {


    private static Javalin app;

    private static final String BASE_URL = "http://localhost:7777/api/v1";

    private static EntityManagerFactory emfTest;

    private Route ride1, ride2, ride3;
    private UserMock passenger, driverMock;
    private Driver driver;
    private RideRequest r1, r2, r3;


    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);

    }

    @BeforeEach
    void setup(){
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createNativeQuery("TRUNCATE TABLE public.ride_request RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock_route RESTART IDENTITY CASCADE").executeUpdate();
            // Insert test data
            passenger = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");
            driverMock = new UserMock("driver@driversen", "driver123", "Driver", "Driversen");
            driver = new Driver(driverMock, "LN12345");
            em.persist(passenger);
            em.persist(driver);
            ride1 = new Route(driver, 2200, 1172, "Rovsingsgade 31", "Nørregade 10", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            ride2 = new Route(driver, 2000, 1172, "Duevej 92", "Nørregade 10", 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            ride3 = new Route(driver, 2000, 1172, "Frederiksvej 10", "Nørregade 10", 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            Route[] routeArray = {ride1, ride2, ride3};
            for(Route r: routeArray){
                em.persist(r);
                driverMock.addRide(r);
            }

            r1 = new RideRequest(passenger, driverMock, ride1);
            r2 = new RideRequest(passenger, driverMock, ride2);
            r3 = new RideRequest(passenger, driverMock, ride3);

            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
        }
    }
    @AfterAll
    void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    /**
     * Integration testing of our endpoint: /route/available_routes
     * The output is a list of 2 available routes sorted by distance to the passengers start location
     * @author pelle112112
     */
    @Test
    void calculateRoutes(){
        List<Route> routeList =
                given()
                .contentType("application/json")
                .body("{\"startLocation\": \"Duevej,22,2000\", \"endLocation\": \"Nørregade,10,1172\"}")
                .when()
                .post(BASE_URL + "/rides/available_routes")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().jsonPath().getList("", Route.class);
        assertEquals(2, routeList.size());
        assertEquals("Duevej 92", routeList.get(0).getStartLocation());
        assertEquals(2000, routeList.get(0).getStartPostalCode());
        assertEquals("Frederiksvej 10", routeList.get(1).getStartLocation());
        assertNotEquals("Frederiksvej 11", routeList.get(1).getStartLocation());
    }

    @Test
    void createRideRequest(){
        RideRequestDTO rideRequest =
                given()
                        .contentType("application/json")
                        .body("{\"rideRequestSenderID\": 2, \"rideRequestReceiverID\": 2, \"rideID\": 3}")
                        .when()
                        .post(BASE_URL + "/requests/requests")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract().body().jsonPath().getObject("", RideRequestDTO.class);
        assertEquals(new RideRequestID(2, 3), rideRequest.getId());
    }

    @Test
    void getAllRequestsForUser(){
        List<RideRequestDTO> rideRequestDTOS =
                given()
                        .contentType("application/json")
                        .when()
                        .get(BASE_URL + "/requests/1")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", RideRequestDTO.class);
        assertEquals(new RideRequestID(1, 1), rideRequestDTOS.get(0).getId());
        assertEquals(3, rideRequestDTOS.size());
    }

}
