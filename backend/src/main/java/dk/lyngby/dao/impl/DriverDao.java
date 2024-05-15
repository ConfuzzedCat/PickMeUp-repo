package dk.lyngby.dao.impl;

import java.util.List;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DriverDao implements IDao<Driver, Integer>{

    Driver driver = new Driver();

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    
    @Override
    public List<Driver> readAll() {
     try(EntityManager em = emf.createEntityManager()){
         return em.createQuery("SELECT d FROM Driver d", Driver.class).getResultList();
     

        }
    }

    public Driver read(Integer id) throws ApiException {
       if (id == null) {
            throw new ApiException(400, "Primary key must be an integer");
        }
        try (var em = emf.createEntityManager())
        {
            return em.find(Driver.class, id);
        }
    }
    

    @Override
    public Driver getById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Driver.class, id);
        }

    return driver;
    }


    @Override
    public Driver create(Driver driver) {
        driver = new Driver(driver.getEmail(), driver.getFullname(), driver.getPassword(), driver.getAdress());
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();

            return driver;
        }
    }

    public Driver update(Driver driver) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            driver = em.find(Driver.class, driver.getId());
            if (driver != null) {
                driver.setEmail(driver.getEmail());
                driver.setFullname(driver.getFullname());
                driver.setPassword(driver.getPassword());
                driver.setAdress(driver.getAdress());
            em.merge(driver);
            em.getTransaction().commit();
            return driver;
            

        }else {

            return null;
        }
    
        }
    }


    public Driver delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            driver = em.find(Driver.class, id);
            if (driver != null) {
                em.remove(driver);
                em.getTransaction().commit();
            }

            
        }

        return driver;

    }


}

    

