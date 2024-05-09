package dk.lyngby.config;

import dk.lyngby.model.Route;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalTime;



public class PopulateRouteDB {


    static List<Route> routes = new ArrayList<>();




    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        routes = getRoutes();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            for (Route route : routes) {
                em.persist(route);
            }

            em.getTransaction().commit();
        }

    }


    @NotNull
    private static List<Route> getRoutes() {
        Route route1 = new Route("Start1", "End1", 1, 10.2, 30, true, 3, 5, LocalTime.of(10, 30));
        Route route2 = new Route("Start2", "End2", 2, 8.2, 25, false, 2, 3, LocalTime.of(8, 15));
        Route route3 = new Route("Start3", "End3", 3, 15.0, 40, true, 5, 7, LocalTime.of(9, 0));
        Route[] routeArray = {route1, route2, route3};
        return List.of(routeArray);
    }

}
