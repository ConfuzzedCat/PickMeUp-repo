package dk.lyngby.dao.impl;

import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.RideRequestID;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DriverRideRequestDAO {

    private static EntityManagerFactory emf;
    private static DriverRideRequestDAO instance;

    /**
     * Singleton pattern
     */
    public static DriverRideRequestDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverRideRequestDAO();
        }
        return instance;
    }

    private DriverRideRequestDAO() {
    }

    /**
     * Method to accept a ride request.
     * @param requestSenderID ID of the user who sent the request.
     * @param rideID ID of the ride.
     * @throws ApiException if the ride request is not found or any other issue occurs.
     */
    public void acceptRideRequest(int requestSenderID, int rideID) throws ApiException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RideRequestID id = new RideRequestID(requestSenderID, rideID);
            RideRequest rideRequest = em.find(RideRequest.class, id);
            if (rideRequest == null) {
                throw new ApiException(404, "Ride request not found");
            }

            Route route = rideRequest.getRide();
            if (route.getPassengerAmount() >= route.getCarSize()) {
                throw new ApiException(400, "No available seats in this ride");
            }

            rideRequest.setAccepted(true);
            em.merge(rideRequest);

            // Update the passenger count
            route.setPassengerAmount(route.getPassengerAmount() + 1);
            em.merge(route);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(500, "Failed to accept ride request: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Method to decline a ride request.
     * @param requestSenderID ID of the user who sent the request.
     * @param rideID ID of the ride.
     * @throws ApiException if the ride request is not found or any other issue occurs.
     */
    public void declineRideRequest(int requestSenderID, int rideID) throws ApiException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RideRequestID id = new RideRequestID(requestSenderID, rideID);
            RideRequest rideRequest = em.find(RideRequest.class, id);
            if (rideRequest == null) {
                throw new ApiException(404, "Ride request not found");
            }
            em.remove(rideRequest);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(500, "Failed to decline ride request: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
