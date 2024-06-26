package dk.lyngby.controller.impl;

import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.UserMock;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashMap;
import java.util.List;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;




class RouteControllerTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1/rides/";
    private static RouteController routeController;
    private static EntityManagerFactory emfTest;
    private static Route r1, r2, r3;
    private static Driver d1;
    private static HashMap<Route, Double> chosenRoute;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        routeController = new RouteController();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp(){
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows

            em.createNativeQuery("TRUNCATE TABLE public.ride_request RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock_route RESTART IDENTITY CASCADE").executeUpdate();
            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.createQuery("DELETE FROM Driver d").executeUpdate();
            // Reset sequence

            // Insert test data
            UserMock driver = new UserMock("driver@driversen", "driver123", "John", "Johnson");
            d1 = new Driver(driver, "LN123456");
            r1 = new Route(d1, 1, 2,"Start1", "End1", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            r2 = new Route(d1, 2, 1,"Start2", "End2", 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
            r3 = new Route(d1, 1, 2, "Start3", "End3", 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
            em.persist(d1);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            chosenRoute = new HashMap<>();

            chosenRoute.put(r1, 117.00);
            chosenRoute.put(r2, 450.0);
            chosenRoute.put(r3, 1500.0);

            em.getTransaction().commit();
        }

    }

    @AfterEach
    void afterEach() {
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void searchFilters() {

        JsonObject filters = new JsonObject();
        filters.addProperty("startLocation", "Start1");

        given().
                contentType(ContentType.JSON)
                .body(filters.toString()).
                when().
                post(BASE_URL + "search").
                then().
                statusCode(200).
                body("size()", is(1)).
                body("[0].startLocation", equalTo("Start1"));
    }

    @Test
    void sortedRoutes() {
        List<Route> sortedRoutes = routeController.sortedRoutes(chosenRoute, 500);

        assertEquals("Start1",sortedRoutes.get(0).getStartLocation());
        assertEquals("Start2",sortedRoutes.get(sortedRoutes.size()-1).getStartLocation());
        assertEquals(2, sortedRoutes.size());
    }
}