package dk.lyngby.dao.impl;

import dk.lyngby.dto.RouteDTO;
import dk.lyngby.model.Route;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public class RouteDAO implements IDao<Route, Integer> {

    private static EntityManagerFactory emf ;

    private static RouteDAO instance;

    private RouteDAO(){}

    public static RouteDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new RouteDAO();
        }
        return instance;
    }

    /**
     * This method returns a list of routes based on the endLocation + endPostalCode and passengerStartPostalCode
     * @param endLocation is the ending location that the passenger decides
     * @param passengerStartPostalCode is the starting postal code for the passenger
     * @return List<Route> List of routes
     * @author pelle112112
     */
    public List<Route> getPassengerRoutesWithFilter (String endLocation, int endPostalCode, int passengerStartPostalCode) {

        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("SELECT r FROM " + Route.class.getSimpleName() + " r WHERE r.endLocation LIKE :endLocation AND r.endPostalCode = :endPostalCode AND r.startPostalCode = :passengerStartPostalCode", Route.class);
            query.setParameter("endLocation", endLocation.replaceAll(",", " "));
            query.setParameter("passengerStartPostalCode", passengerStartPostalCode);
            query.setParameter("endPostalCode", endPostalCode);
            List<Route> allFound = query.getResultList();
            for(Route r: allFound){
                r.getRideRequests().size();
            }
            return allFound;
        }
    }


    @Override
    public Route read(Integer id) throws ApiException {
        if (id == null) {
            throw new ApiException(400, "Primary key must be an integer");
        }
        try (var em = emf.createEntityManager())
        {
            Route ride = em.find(Route.class, id);
            ride.getRideRequests().size();
            return ride;
        }
    }



    @Override
    public List readAll() {
        try (var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT r FROM Route r", Route.class);
            return query.getResultList();
        }
    }

    @Override
    public Route create(Route route) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(route);
            em.getTransaction().commit();
            return route;
        }
    }

    @Override
    public Route update(Integer id, Route updatedRoute) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Route route = em.find(Route.class, id);
            route.setStartLocation(updatedRoute.getStartLocation());
            route.setEndLocation(updatedRoute.getEndLocation());
            route.setDepartureTime(updatedRoute.getDepartureTime());
            em.merge(route);
            em.getTransaction().commit();
            return route;
        }
    }

    @Override
    public void delete(Integer id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Route route = em.find(Route.class, id);
            em.remove(route);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return false;
    }

    /** create a search filter for the routes that searches for routes based
     on multiple criteria such as start location, end location, driverId,
     route length, time in minutes, handicap availability, passenger amount,
     car size, or departure time **/

    public List<Route> searchFilters(JsonObject filters) throws Exception {
        StringBuilder queryString = new StringBuilder("SELECT r FROM Route r WHERE 1 = 1");
        TypedQuery<Route> query;

        try (EntityManager em = emf.createEntityManager()) {
            if (filters != null) {
                filters.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    if (key.equals("start")) {
                        queryString.append(" AND r.").append("departureTime").append(" >= :").append(key);
                    } else if (key.equals("end")) {
                        queryString.append(" AND r.").append("departureTime").append(" <= :").append(key);
                    } else {
                        queryString.append(" AND r.").append(key).append(" = :").append(key);
                    }
                });

                query = em.createQuery(queryString.toString(), Route.class);

                filters.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    if (entry.getValue() != null) {
                        Object value = getValue(entry.getValue());
                        query.setParameter(key, value);
                    }
                });
            } else {
                query = em.createQuery("SELECT r FROM Route r", Route.class);
            }

            return query.getResultList();
        }
    }

    private Object getValue(JsonElement value) {
        if (value.isJsonPrimitive()) {
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                // Return boolean value as is
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                // Check if it's an integer or double
                if (primitive.getAsNumber().doubleValue() == primitive.getAsNumber().intValue()) {
                    // If it's an integer value, return as int
                    return primitive.getAsNumber().intValue();
                } else {
                    // If it's a decimal value, return as double
                    return primitive.getAsNumber().doubleValue();
                }
            } else if (primitive.isString()) {
                // Check if it's a time value
                String stringValue = primitive.getAsString();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    // Parse the LocalDateTime string
                    return LocalDateTime.parse(stringValue, formatter);
                } catch (DateTimeParseException e) {
                    // If parsing as LocalTime fails, return as string
                    return stringValue;
                }
            }
        }
        return null;
    }


}
