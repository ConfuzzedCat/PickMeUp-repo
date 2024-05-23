package dk.lyngby.controller.impl;

import com.google.gson.JsonObject;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import io.javalin.Javalin;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ReviewControllerTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1/reviews/";
    private static EntityManagerFactory emfTest;
    private static Route route;
    private static UserMock user;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp() {
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM RideRequest r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.createQuery("DELETE FROM UserMock u").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            // Insert test data
            route = new Route(1, 2, "Start1", "End1", 1, 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            user = new UserMock("john.doe@example.com", "password123", "John", "Doe");
            em.persist(route);
            em.persist(user);
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void createReview() {
        JsonObject reviewJson = new JsonObject();
        reviewJson.addProperty("routeId", route.getId());
        reviewJson.addProperty("userId", user.getId());
        reviewJson.addProperty("title", "Great Ride");
        reviewJson.addProperty("description", "The ride was smooth and comfortable.");
        reviewJson.addProperty("rating", 4.5);

        given().
                contentType(ContentType.JSON)
                .body(reviewJson.toString()).
                when().
                post(BASE_URL).
                then().
                statusCode(201).
                body("title", equalTo("Great Ride"))
                .body("description", equalTo("The ride was smooth and comfortable."))
                .body("rating", equalTo(4.5f))
                .body("route.startLocation", equalTo("Start1"))
                .body("user.firstName", equalTo("John"));
    }

    @Test
    void createReviewForNonExistentUser() {
        JsonObject reviewJson = new JsonObject();
        reviewJson.addProperty("routeId", route.getId());
        reviewJson.addProperty("userId", 999); // Non-existent user ID
        reviewJson.addProperty("title", "Great Ride");
        reviewJson.addProperty("description", "The ride was smooth and comfortable.");
        reviewJson.addProperty("rating", 4.5);

        given().
                contentType(ContentType.JSON)
                .body(reviewJson.toString()).
                when().
                post(BASE_URL).
                then().
                statusCode(400)
                .body("message", equalTo("User is not a passenger on this ride"));
    }
}
