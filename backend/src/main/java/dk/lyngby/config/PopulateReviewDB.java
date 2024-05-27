package dk.lyngby.config;

import dk.lyngby.model.Driver;
import dk.lyngby.model.Review;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashSet;

public class PopulateReviewDB {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        UserMock reviewer1 = new UserMock("user1@example.com", "password1", "User", "One");
        UserMock reviewer2 = new UserMock("user2@example.com", "password2", "User", "Two");
        Driver driver1 = new Driver("email@email.dk", "John Doe", "password", "address", "ABC123", new HashSet<>());

        Route route1 = new Route(driver1, 2200, 1172, "Lyngby Hovedgade", "Dtu", 12.0, 20, true, 4, 4, LocalDateTime.now());
        Route route2 = new Route(driver1, 2800, 1172, "Nørreport", "Kgs. Nytorv", 3.0, 10, false, 2, 3, LocalDateTime.now());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();


            em.persist(reviewer1);
            em.persist(reviewer2);
            em.persist(driver1);
            em.persist(route1);
            em.persist(route2);


            Review review1 = new Review(route1, reviewer1, "Excellent Ride", "The driver was very polite and the ride was smooth.", 5.0);
            Review review2 = new Review(route2, reviewer2, "Good, but could be better", "The ride was quick but the car was not very clean.", 3.5);


            em.persist(review1);
            em.persist(review2);

            em.getTransaction().commit();
        }
    }
}
