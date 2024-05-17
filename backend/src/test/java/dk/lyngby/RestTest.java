package dk.lyngby;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import dk.lyngby.model.Route;
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

    private static Route r1, r2, r3;


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
            // Reset sequence
            //em.createNativeQuery("ALTER SEQUENCE id RESTART WITH 1").executeUpdate();
            // Insert test data
            r1 = new Route(2200, 1172,"Rovsingsgade 31", "Nørregade 10", 1, 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            r2 = new Route(2000, 1172, "Duevej 92", "Nørregade 10", 2, 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            r3 = new Route(2000, 1172, "Frederiksvej 10", "Nørregade 10", 3, 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
        }
    }
    @AfterEach
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



}
