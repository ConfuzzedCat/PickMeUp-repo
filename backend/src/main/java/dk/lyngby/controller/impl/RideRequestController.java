package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.RideRequestDAO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.RideRequest;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class RideRequestController implements IController<RideRequest, Integer> {

    private RideRequestDAO dao = RideRequestDAO.getInstance(HibernateConfig.getEntityManagerFactory());

    /**
     * This method returns a list of all Requests for a specific user to the /requests/{userid} endpoint.
     *
     * @param ctx
     * @throws ApiException
     * @author pelle112112
     */
    public void getAllRequestsByUserId(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("userid", Integer.class).get();
        List<RideRequest> requests;

        //Get the Requests from the DB
        requests = dao.getRideRequestsForUser(id);


        ctx.json(requests);
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
        RideRequest request = ctx.bodyAsClass(RideRequest.class);

        //Persist to DB - exception is thrown if request already exists.
        dao.create(request);

        ctx.json(request);
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
