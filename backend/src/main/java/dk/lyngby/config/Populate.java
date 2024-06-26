package dk.lyngby.config;

import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        truncateRoutes(emf);
        //populateRoutes(emf);
    }

    /**
     * Populates the "route" table with mock data
     *
     * @param emf Entitymanagerfactory needed for the Entitymanager
     * @author pelle112112
     */
    /*@NotNull
    private static void populateRoutes(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            // Insert test rows
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('1','Nørregade 10','1172','Rovsingsgade 31','2200');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('2','Nørregade 10','1172','Duevej 92','2000');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('3','Nørregade 10','1172','Frederiksvej 10','2000');").executeUpdate();

            em.getTransaction().commit();
        }
    }*/

    /**
     * Truncates the "route" table
     *
     * @param emf Entitymanagerfactory needed for the Entitymanager
     * @author pelle112112
     */
    private static void truncateRoutes(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY").executeUpdate();
            em.getTransaction().commit();

        }
    }


    public static void createDrivers(EntityManagerFactory emf) {
        try {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            // Create or fetch Driver objects
            Driver driver1 = new Driver("email@email.dk", "John Doe", "password", "address", "ABC123", new HashSet<>());
            em.persist(driver1);

            Driver driver2 = new Driver("email2@email.dk", "Jane Doe", "password", "address", "ABC143", new HashSet<>());
            em.persist(driver2);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createRoutes(EntityManagerFactory emf) {
        try {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            // Create or fetch Driver objects
            Driver driver1 = new Driver("email@email.dk", "John Doe", "password", "address", "ABC123", new HashSet<>());
            em.persist(driver1);

            Driver driver2 = new Driver("email2@email.dk", "Jane Doe", "password", "address", "ABC143", new HashSet<>());
            em.persist(driver2);

            // sample routes with associated drivers
            LocalDateTime departureDateTime1 = LocalDateTime.of(2024, 5, 10, 8, 0);
            Route route1 = new Route(driver1, 10001, 90001, "New York", "Los Angeles",
                    300.5, 360, true, 4, 2, departureDateTime1);
            em.persist(route1);

            LocalDateTime departureDateTime2 = LocalDateTime.of(2024, 5, 10, 8, 0);
            Route route2 = new Route(driver2, 10001, 90001, "New Jersey", "Los Hermanos",
                    300.5, 360, true, 4, 2, departureDateTime2);
            em.persist(route2);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
