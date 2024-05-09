package dk.lyngby.dao.impl;

import dk.lyngby.model.Route;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RouteDao {

    private static RouteDao instance;
    private static EntityManagerFactory emf;

    public static RouteDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RouteDao();
        }
        return instance;
    }
    /** creat a search filter for the routes that searches for routes based
     on multiple criteria such as start location, end location, driverId,
     route length, time in minutes, handicap availability, passenger amount,
     car size, or departure time **/

    public List<Route> searchFilters (String startLocation, String endLocation, int driverId,
                                      double routeLength, int timeInMinutes,
                                      boolean handicapAvailability, int passengerAmount,
                                      int carSize, String departureTime) {

        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT r FROM Route r WHERE r.startLocation = :startLocation " +
                    "OR r.endLocation = :endLocation " +
                    "OR r.driverId = :driverId " +
                    "OR r.routeLength = :routeLength " +
                    "OR r.timeInMinutes = :timeInMinutes " +
                    "OR r.handicapAvailability = :handicapAvailability " +
                    "OR r.passengerAmount = :passengerAmount " +
                    "OR r.carSize = :carSize " +
                    "OR r.departureTime = :departureTime", Route.class);
            query.setParameter("startLocation", startLocation);
            query.setParameter("endLocation", endLocation);
            query.setParameter("driverId", driverId);
            query.setParameter("routeLength", routeLength);
            query.setParameter("timeInMinutes", timeInMinutes);
            query.setParameter("handicapAvailability", handicapAvailability);
            query.setParameter("passengerAmount", passengerAmount);
            query.setParameter("carSize", carSize);
            query.setParameter("departureTime", departureTime);
            return query.getResultList();
        }
    }


}
