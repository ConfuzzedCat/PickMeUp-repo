package dk.lyngby.dao.impl;

import java.util.List;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Driver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DriverDao {

    Driver driver = new Driver();

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();



    
    public Driver getById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Driver.class, id);
        }
    }

    public List<Driver> getalldDrivers() {
      
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT * FROM Driver d", Driver.class).getResultList();
        }
    }


  
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
                driver.setAddress(driver.getAddress());
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

    

