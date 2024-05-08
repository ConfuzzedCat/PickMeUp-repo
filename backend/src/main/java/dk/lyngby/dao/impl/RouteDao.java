package dk.lyngby.dao.impl;

import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RouteDao implements dk.lyngby.dao.IDao<Route, Long> {

    private static RouteDao instance;
    private static EntityManagerFactory emf;

    public static RouteDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RouteDao();
        }
        return instance;
    }

    @Override
    public Route read(Long id) throws ApiException {
        try (var em = emf.createEntityManager()) {
            Route route = em.find(Route.class, id);
            if (route == null) {
                throw new ApiException(404, "Route not found");
            }
            return route;
        }
    }

    @Override
    public List<Route> readAll() {
        try (var em = emf.createEntityManager()) {
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
    public Route update(Long id, Route updatedRoute) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Route route = em.find(Route.class, id);
            route.setDepartureLocation(updatedRoute.getDepartureLocation());
            route.setDestination(updatedRoute.getDestination());
            route.setDepartureDateTime(updatedRoute.getDepartureDateTime());
            em.merge(route);
            em.getTransaction().commit();
            return route;
        }
    }

    @Override
    public void delete(Long id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Route route = em.find(Route.class, id);
            em.remove(route);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (var em = emf.createEntityManager()) {
            Route route = em.find(Route.class, id);
            return route != null;
        }
    }
}
