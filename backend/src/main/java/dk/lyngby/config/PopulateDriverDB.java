package dk.lyngby.config;
import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalTime;

public class PopulateDriverDB {
    static List<Driver> drivers = new ArrayList<>();


    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    drivers = getDrivers();

        try (var em = emf.createEntityManager()) {
        em.getTransaction().begin();

        for (Driver driver : drivers) {
            em.persist(driver);
        }

        em.getTransaction().commit();
    }

}


    @NotNull
    private static List<Driver> getDrivers() {
        Driver driver1 = new Driver("name", "test", "test", "test", "test".getBytes(), "test".getBytes());
        Driver driver2 = new Driver("name2", "test2", "test2", "test2", "test2".getBytes(), "test2".getBytes());
        Driver driver3 = new Driver("name3", "test3", "test3", "test3", "test3".getBytes(), "test3".getBytes());
        Driver[] driverArray = {driver1, driver2, driver3};
        return List.of(driverArray);
    }

}