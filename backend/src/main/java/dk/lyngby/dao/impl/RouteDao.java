package dk.lyngby.dao.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
