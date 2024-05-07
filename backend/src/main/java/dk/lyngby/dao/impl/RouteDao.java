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

    /**
     * This method returns a list of routes based on the endLocation + endPostalCode and passengerStartPostalCode
     * @param endLocation is the ending location that the passenger decides
     * @param passengerStartPostalCode is the starting postal code for the passenger
     * @return List<Route> List of routes
     * @author pelle112112
     */
    public List<Route> getPassengerRoutesWithFilter (String endLocation, int endPostalCode, int passengerStartPostalCode) {
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("SELECT r FROM " + Route.class.getSimpleName() + " r WHERE r.endLocation = :endLocation AND r.endPostalCode = :endPostalCode AND r.startPostalCode = :passengerStartPostalCode", Route.class);
            query.setParameter("endLocation", endLocation);
            query.setParameter("passengerStartPostalCode", passengerStartPostalCode);
            query.setParameter("endPostalCode", endPostalCode);
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
