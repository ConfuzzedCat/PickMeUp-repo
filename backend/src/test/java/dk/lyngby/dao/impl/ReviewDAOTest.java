package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Driver;
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
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDAOTest {

    private static EntityManagerFactory emfTest;
    private static ReviewDAO reviewDAO;
    private static Review review;
    private static UserMock user;
    private static Route route;
    private static Javalin app;

    @BeforeAll
    static void beforeAll() {
        emfTest = HibernateConfig.getEntityManagerFactory();
        reviewDAO = ReviewDAO.getInstance(emfTest);
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
            em.createNativeQuery("TRUNCATE TABLE public.driver RESTART IDENTITY CASCADE").executeUpdate();

            em.createQuery("DELETE FROM Review r").executeUpdate();
            em.createQuery("DELETE FROM Route r").executeUpdate();
            em.createQuery("DELETE FROM UserMock r").executeUpdate();
            em.createQuery("DELETE FROM Driver r").executeUpdate();


            em.createNativeQuery("ALTER SEQUENCE route_route_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE usermock_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE review_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE driver_driver_id_seq RESTART WITH 1").executeUpdate();

            // insert data
            Driver driver = new Driver("driver@driversen", "driver123", "John", "Johnson", "LN123456", new HashSet<>());
            route = new Route(driver, 1, 2, "Start1", "End1", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
            user = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");

            em.persist(driver);
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
    void read() throws ApiException {

        assertEquals(1,reviewDAO.read(1).getId());
    }

    @Test
    void readAll() {
        assertEquals(1, reviewDAO.readAll().size());
    }

    @Test
    void create() {
        Review review1 = new Review(route, user, "Great driver ","Great driver, would recommend", 5.0);
        reviewDAO.create(review1);
        assertEquals(2, reviewDAO.readAll().size());
    }

    @Test
    void update() throws ApiException {
        review.setDescription("Great ride, would recommend");
        reviewDAO.update(1, review);
        assertEquals("Great ride, would recommend", reviewDAO.read(review.getId()).getDescription() );
    }

    @Test
    void delete() {
        reviewDAO.delete(review.getId());
        assertEquals(0, reviewDAO.readAll().size());
    }
}