package dk.lyngby.dao.impl;

import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.RideRequestID;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @Author MrJustMeDahl
 * This class holds all the methods that use queries to retrieve and input RideRequest data in the database.
 */
public class RideRequestDAO {

    private static EntityManagerFactory emf;

    private static RideRequestDAO instance;

    /**
     * @Author MrJustMeDahl
     * Singleton pattern
     */
    public static RideRequestDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new RideRequestDAO();
        }
        return instance;
    }

    /**
     * @Author MrJustMeDahl
     * Method for retrieving a single RideRequest from the database based on it's ID.
     * @param requestSenderID user ID for the passenger who sends the request.
     * @param rideID Ride ID for the ride the passenger wants to join.
     * @return a RideRequest fetched from the DB if it exists.
     * @throws ApiException if there is no RideRequest in the database with the given ID.
     */
    public RideRequest readByID(int requestSenderID, int rideID) throws ApiException {
        RideRequest rideRequest;
        RideRequestID id = new RideRequestID(requestSenderID, rideID);
        try(EntityManager em = emf.createEntityManager()){
            rideRequest = em.find(RideRequest.class, id);
        }
        if(rideRequest == null){
            throw new ApiException(404, "Failed to retrieve request with ID: " + id);
        }
        return rideRequest;
    }

    /**
     * @Author MrJustMeDahl
     * Method for retrieving every single RideRequest in the database.
     * @return List of all RideRequests
     * @throws ApiException if there is no list to return because of unknown reason or if there is no RideRequests in the system at all.
     */
    public List<RideRequest> readAll() throws ApiException {
        List<RideRequest> allRideRequests;
        try(EntityManager em = emf.createEntityManager()){
           allRideRequests = em.createNamedQuery("RideRequest.getAll").getResultList();
        }
        if(allRideRequests == null){
            throw new ApiException(500, "Something went wrong with the database.");
        } else if(allRideRequests.isEmpty()){
            throw new ApiException(200, "There is no requests in the system at the moment.");
        }
        return allRideRequests;
    }

    /**
     * @Author MrJustMeDahl
     * Used for creating a new entry of a RideRequest in the DB.
     * @param rideRequest A new RideRequest ready to be put into the DB.
     * @return The newly persisted RideRequest entry.
     * @throws ApiException if there is already a RideRequest in the database from the same passenger to the same ride.
     */
    public RideRequest create(RideRequest rideRequest) throws ApiException{
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            rideRequest.setRequestSender(em.find(UserMock.class, rideRequest.getRequestSender().getId()));
            rideRequest.setRequestReceiver(em.find(UserMock.class, rideRequest.getRequestSender().getId()));
            rideRequest.setRide(em.find(Route.class, rideRequest.getRide().getId()));
            em.merge(rideRequest.getRequestSender());
            em.merge(rideRequest.getRequestReceiver());
            em.merge(rideRequest.getRide());
            em.persist(rideRequest);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            throw new ApiException(409, "You have already applied to join this ride.");
        }
        return rideRequest;
    }

    /**
     * @Author: MrJustMeDahl
     * Method used for retrieving all RideRequests that a single user has sent.
     * @param userID ID of the user.
     * @return List of RideRequest that the user has sent.
     * @throws ApiException if there is no list for some reason, or if there is no RideRequests for the given user.
     */
    public List<RideRequest> getRideRequestsForUser(int userID) throws ApiException{
        List<RideRequest> requestsForUser;
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createNativeQuery("SELECT * FROM public.ride_request WHERE request_sender = :userID", RideRequest.class);
            query.setParameter("userID", userID);
            requestsForUser = query.getResultList();
        }
        if(requestsForUser == null){
            throw new ApiException(500, "Something went wrong with the database.");
        } else if(requestsForUser.isEmpty()){
            throw new ApiException(404, "There is no requests in the system for user with ID: " + userID);
        }
        return requestsForUser;
    }

}
