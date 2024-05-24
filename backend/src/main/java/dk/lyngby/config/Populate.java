package dk.lyngby.config;

import dk.lyngby.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        createDrivers(emf);
        createUsers(emf);
        createRoutes(emf);
        createReviews(emf);
        createRequests(emf);

    }

    public static void createDrivers(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Driver driver1 = new Driver("John", "Doe", "test", "test", "22", null);
            Driver driver2 = new Driver("Jane", "Doe", "test", "test", "wowoad", null);
            Driver driver3 = new Driver("John", "Smith", "test", "test", "doaw", null);
            em.persist(driver1);
            em.persist(driver2);
            em.persist(driver3);
            em.getTransaction().commit();
        }
    }

    public static void createUsers(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            UserMock user1 = new UserMock("email1", "password1", "Bob", "Doe");
            UserMock user2 = new UserMock("email2", "password2", "Jens", "Doe");
            UserMock user3 = new UserMock("email3", "password3", "Chad", "Smith");
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.getTransaction().commit();

        }
    }

    public static void createRoutes(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Driver driver1 = em.find(Driver.class, 1);
            Driver driver2 = em.find(Driver.class, 2);
            Driver driver3 = em.find(Driver.class, 3);
            Route route1 = new Route(driver1, 2200, 1172, "Lyngby Hovedgade", "Dtu", 12.0, 20, true, 4, 4, LocalDateTime.now());
            Route route2 = new Route(driver2, 2800, 1172, "Nørreport", "Kgs. Nytorv", 3.0, 10, false, 2, 3, LocalDateTime.now());
            Route route3 = new Route(driver3, 2800, 1172, "Nørreport", "Kgs. Nytorv", 3.0, 10, false, 2, 3, LocalDateTime.now());
            em.persist(route1);
            em.persist(route2);
            em.persist(route3);
            em.getTransaction().commit();
        }
    }

    public static void createRequests(EntityManagerFactory enf) {
        try (var em = enf.createEntityManager()) {
            em.getTransaction().begin();
            UserMock passenger = em.find(UserMock.class, 1);
            Driver driver = em.find(Driver.class, 1);
            Route route1 = em.find(Route.class, 1);
            RideRequest r1 = new RideRequest(passenger, driver, route1);
            em.persist(r1);
            em.getTransaction().commit();
        }
    }

    public static void createReviews(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            UserMock reviewer1 = em.find(UserMock.class, 1);
            UserMock reviewer2 = em.find(UserMock.class, 2);
            UserMock reviewer3 = em.find(UserMock.class, 3);
            Route route1 = em.find(Route.class, 1);
            Route route2 = em.find(Route.class, 2);
            Route route3 = em.find(Route.class, 3);
            Review review1 = new Review(route1, reviewer1, "Excellent Ride", "The driver was very polite and the ride was smooth.", 5.0);
            Review review2 = new Review(route2, reviewer2, "Good, but could be better", "The ride was quick but the car was not very clean.", 3.5);
            Review review3 = new Review(route3, reviewer3, "Excellent Ride", "The driver was very polite and the ride was smooth.", 5.0);
            em.persist(review1);
            em.persist(review2);
            em.persist(review3);
            em.getTransaction().commit();
        }
    }

}
