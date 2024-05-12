package dk.lyngby.config;

import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class Populate {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Create or fetch Driver objects
            Driver driver1 = new Driver("John Doe", "XYZ123");
            em.persist(driver1);

            Driver driver2 = new Driver("Jane Smith", "ABC456");
            em.persist(driver2);

            // sample routes with associated drivers
            Route route1 = new Route("New York", "Los Angeles", LocalDateTime.of(2024, 5, 10, 8, 0));
            route1.setDriver(driver1); // Set driver for route1
            em.persist(route1);

            Route route2 = new Route("London", "Paris", LocalDateTime.of(2024, 5, 15, 10, 30));
            route2.setDriver(driver2); // Set driver for route2
            em.persist(route2);

            // Commit 
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
