package dk.lyngby.controller.impl;

import com.google.gson.JsonObject;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Route;
import io.javalin.Javalin;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeAll
    static void beforeAll() {
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
}