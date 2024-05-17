package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.Route;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class RideRequestTest {

    private static Javalin app;
    private static EntityManagerFactory emfTest;
    private static RideRequestDAO dao;
    private RideRequest r1, r2, r3;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        dao = RideRequestDAO.getInstance(emfTest);
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp() {
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM RideRequest r").executeUpdate();
            // Reset sequence
            //em.createNativeQuery("ALTER SEQUENCE id RESTART WITH 1").executeUpdate();
            // Insert test data
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
}
