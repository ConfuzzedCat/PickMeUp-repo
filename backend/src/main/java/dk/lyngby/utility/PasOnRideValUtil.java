package dk.lyngby.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

public class PasOnRideValUtil {

    private static EntityManagerFactory emf;

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        emf = entityManagerFactory;
    }

    public static boolean isUserPassengerOnRide(int userId, int routeId) {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT COUNT(r) FROM RideRequest r WHERE r.id.requestSenderID = :userId AND r.id.rideID = :routeId AND r.accepted = true");
            query.setParameter("userId", userId);
            query.setParameter("routeId", routeId);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        }
    }
}
