package dk.lyngby.dao.impl;

import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.RideRequestID;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class RideRequestDAO {

    private static EntityManagerFactory emf;

    private static RideRequestDAO instance;

    public static RideRequestDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new RideRequestDAO();
        }
        return instance;
    }

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
