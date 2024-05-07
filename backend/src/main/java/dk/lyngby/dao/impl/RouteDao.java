package dk.lyngby.dao.impl;

import dk.lyngby.Model.Route;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

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


    public List<Route> getPassengerRoutesWithFilter (String endLocation, String passengerStartPostalCode, Route route){
        // For the real case we want to define the postal codes that the passenger wants routes for
        // For this prototype we are gonna limit the routes for the DB call to be the passengers postal code +-100
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("SELECT r FROM " + Route.class.getSimpleName() + " r WHERE r.endLocation = :endLocation AND r.startPostalCode = :passengerStartPostalCode", Route.class);
            query.setParameter("endLocation", endLocation);
            query.setParameter("passengerStartPostalCode", passengerStartPostalCode);
            return query.getResultList();
        }
    }

    @Override
    public Object read(Object o) throws ApiException {
        return null;
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
}
