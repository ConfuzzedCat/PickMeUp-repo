package dk.lyngby.dao.impl;

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



public class RouteDao implements IDao {

    protected EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private static RouteDao instance;
    private RouteDao(){}
    public static RouteDao getInstance(){
        if(instance == null){
            instance = new RouteDao();
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
            return query.getResultList();
        }
    }


    @Override
    public Route read(Integer id) throws ApiException {
        if (id == null) {
            throw new ApiException(400, "Primary key must be an integer");
        }
        try (var em = emf.createEntityManager())
        {
            return em.find(Route.class, id);
        }
    }



    @Override
    public List readAll() {
        return null;
    }

    @Override
    public Object create(Object o) {
        return null;
    }

    @Override
    public Object update(Object o, Object o2) {
        return null;
    }

    @Override
    public void delete(Object o) {
    }

    @Override
    public boolean validatePrimaryKey(Object o) {
        return false;
    }

    /** create a search filter for the routes that searches for routes based
     on multiple criteria such as start location, end location, driverId,
     route length, time in minutes, handicap availability, passenger amount,
     car size, or departure time **/

    public List<dk.lyngby.model.Route> searchFilters(JsonObject filters) throws Exception {
        StringBuilder queryString = new StringBuilder("SELECT r FROM Route r WHERE 1 = 1");
        TypedQuery<dk.lyngby.model.Route> query;

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

                query = em.createQuery(queryString.toString(), dk.lyngby.model.Route.class);

                filters.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    if (entry.getValue() != null) {
                        Object value = getValue(entry.getValue());
                        query.setParameter(key, value);
                    }
                });
            } else {
                query = em.createQuery("SELECT r FROM Route r", dk.lyngby.model.Route.class);
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
