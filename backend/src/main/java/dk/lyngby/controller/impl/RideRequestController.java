package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.RideRequestDAO;
import dk.lyngby.dao.impl.RouteDAO;
import dk.lyngby.dao.impl.UserMockDAO;
import dk.lyngby.dto.RideRequestDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class RideRequestController implements IController<RideRequest, Integer> {

    private RideRequestDAO dao = RideRequestDAO.getInstance(HibernateConfig.getEntityManagerFactory());

    /**
     * This method returns a list of all outgoing Requests for a specific user to the /requests/incoming/{userid} endpoint.
     *
     * @param ctx
     * @throws ApiException
     * @author pelle112112
     */
    public void getAllOutgoingRequestsByUserId(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("userid", Integer.class).get();
        List<RideRequest> requests;

        //Get the Requests from the DB
        requests = dao.getOutgoingRideRequestsForUser(id);
        List<RideRequestDTO> dtos = new ArrayList<>();
        for(RideRequest r: requests){
            dtos.add(new RideRequestDTO(r.getId(), r.getRequestSender().getId(), r.getRequestReceiver().getId(), r.getRide().getId()));
        }

        ctx.json(dtos);
        ctx.status(200);
    }

    /**
     * This method returns a list of all incoming Requests for a specific user to the /requests/outgoing/{userid} endpoint.
     * @param ctx
     * @throws ApiException
     * @author MrJustMeDahl
     */
    public void getAllIncomingRequestsByUserId(Context ctx) throws ApiException{
        int id = ctx.pathParamAsClass("userid", Integer.class).get();
        List<RideRequest> requests;

        //Get the Requests from the DB
        requests = dao.getIncomingRideRequestsForUser(id);
        List<RideRequestDTO> dtos = new ArrayList<>();
        for(RideRequest r: requests){
            dtos.add(new RideRequestDTO(r.getId(), r.getRequestSender().getId(), r.getRequestReceiver().getId(), r.getRide().getId()));
        }

        ctx.json(dtos);
        ctx.status(200);
    }


    @Override
    public void read(Context ctx) throws ApiException {

    }

    @Override
    public void readAll(Context ctx) throws ApiException {
        List<RideRequest> allRequests = dao.readAll();
        ctx.json(allRequests);
        ctx.status(200);
    }

    /**
     * This method handles the creation of a new Request at the /requests/requests endpoint.
     *
     * @param ctx
     * @author pelle112112
     */
    @Override
    public void create(Context ctx) throws ApiException {
        UserMockDAO userDAO = UserMockDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        RouteDAO routeDAO = RouteDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        RideRequestDTO dto = ctx.bodyAsClass(RideRequestDTO.class);
        UserMock requestSender = userDAO.read(dto.getRideRequestSenderID());
        UserMock requestReceiver = userDAO.read(dto.getRideRequestReceiverID());
        Route ride = routeDAO.read(dto.getRideID());
        //Persist to DB - exception is thrown if request already exists.
        RideRequest rideRequest = dao.create(new RideRequest(requestSender, requestReceiver, ride));
        dto.setID(rideRequest.getId());
        ctx.json(dto);
        ctx.status(201);
    }

    @Override
    public void update(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }

    @Override
    public RideRequest validateEntity(Context ctx) {
        return null;
    }
}
