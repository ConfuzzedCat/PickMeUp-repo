package dk.lyngby.config;

import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class Populate {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // sample routes
            Route route1 = new Route("New York", "Los Angeles", LocalDateTime.of(2024, 5, 10, 8, 0));
            em.persist(route1);

            Route route2 = new Route("London", "Paris", LocalDateTime.of(2024, 5, 15, 10, 30));
            em.persist(route2);

            // Commit 
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
