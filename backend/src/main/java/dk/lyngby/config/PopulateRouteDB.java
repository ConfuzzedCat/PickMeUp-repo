package dk.lyngby.config;

import dk.lyngby.model.RideRequest;
import dk.lyngby.model.Route;
import dk.lyngby.model.Driver;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



public class PopulateRouteDB {


    static List<Route> routes = new ArrayList<>();




    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        UserMock passenger = new UserMock("test@testesen.dk", "test123", "Test", "Testesen");
        UserMock driver = new UserMock("driver@driversen", "driver123", "Driver", "Driversen");
        Driver driver1 = new Driver("email@email.dk", "John Doe", "password", "address", "ABC123", new HashSet<>());

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(passenger);
            em.persist(driver);
            em.persist(driver1);

            em.getTransaction().commit();
        }

        routes = getRoutes(driver1);

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            driver = em.merge(driver);
            for(Route r: routes){
                em.persist(r);
                driver.addRide(r);
            }
            em.getTransaction().commit();
        }

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            driver = em.merge(driver);
            passenger = em.merge(passenger);
            Route ride1 = em.find(Route.class, routes.get(0).getId());
            Route ride2 = em.find(Route.class, routes.get(1).getId());
            Route ride3 = em.find(Route.class, routes.get(2).getId());
            RideRequest r1 = new RideRequest(passenger, driver, ride1);
            RideRequest r2 = new RideRequest(passenger, driver, ride2);
            RideRequest r3 = new RideRequest(passenger, driver, ride3);

            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
        }

    }


    @NotNull
    private static List<Route> getRoutes(Driver driver) {
        Route route1 = new Route(driver, 2200, 1172, "Rovsingsgade 31", "Nørregade 10", 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
        Route route2 = new Route(driver, 2000, 1172, "Duevej 92", "Nørregade 10", 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
        Route route3 = new Route(driver, 2000, 1172, "Frederiksvej 10", "Nørregade 10", 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));
        Route[] routeArray = {route1, route2, route3};
        return List.of(routeArray);
    }

}
