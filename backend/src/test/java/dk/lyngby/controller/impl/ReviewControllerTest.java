package dk.lyngby.controller.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Review;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import io.javalin.Javalin;
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

class ReviewControllerTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1/reviews";
    private static ReviewController reviewController;
    private static EntityManagerFactory emfTest;

    private static Route route;
    private static UserMock user;
    private static Review review;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        reviewController = new ReviewController();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp(){
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createNativeQuery("TRUNCATE TABLE public.ride_request RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE public.usermock_route RESTART IDENTITY CASCADE").executeUpdate();
            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.createQuery("DELETE FROM UserMock r").executeUpdate();


           em.createNativeQuery("ALTER SEQUENCE route_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE usermock_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE review_id_seq RESTART WITH 1").executeUpdate();
            // incert data
            route = new Route(1, 2,"Start1", "End1", 1, 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            user = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");

            em.persist(route);
            em.persist(user);
            review = new Review (route, user, "Great driver ","Great driver, would recommend", 5.0);

            em.persist(review);


            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void read() {
        given()
                .when()
                .get(BASE_URL + "/" + 1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(review.getId()));
    }

    @Test
    void readAll() {
        given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void create() {
      Review r2 = new Review(route, user, "ok driver ","maybe recommend", 3.0);
      given()
                .contentType("application/json")
                .body(r2)
                .when()
                .post(BASE_URL+"/")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().body().jsonPath().getInt("id");

    }

    @Test
    void update() {
        review.setDescription("Great ride, would recommend");
        given()
                .contentType("application/json")
                .body(review)
                .when()
                .put(BASE_URL + "/" + review.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("description", equalTo("Great ride, would recommend"));
    }

    @Test
    void delete() {
        given()
                .when()
                .delete(BASE_URL + "/" + review.getId())
                .then()
                .assertThat()
                .statusCode(204);
    }


}