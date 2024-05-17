package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Request;
import io.javalin.http.Context;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

public class RequestController implements IController<Request, Integer> {


    /**
     * This method returns a list of all Requests for a specific user to the /requests/{userid} endpoint.
     * @param ctx
     * @throws ApiException
     * @author pelle112112
     */
    public void getAllRequestsByUserId(Context ctx) throws ApiException{
        int id = ctx.pathParamAsClass("userid", Integer.class).get();
        List<Request> requests = new ArrayList<>();

        //Get the Requests from the DB
        //todo: implement DAO Method

        ctx.json(requests);
        ctx.status(200);
    }


    @Override
    public void read(Context ctx) throws ApiException {

    }

    @Override
    public void readAll(Context ctx) {

    }

    /**
     * This method handles the creation of a new Request at the /requests/requests endpoint.
     * @param ctx
     * @author pelle112112
     */
    @Override
    public void create(Context ctx) {
        Request request = ctx.bodyAsClass(Request.class);

        //Check if the user has already requested for the specific route
        Boolean requestAlreadyExists = false;
        if(requestAlreadyExists){
            ctx.status(403);
        }
        else {
            //Persist to DB
            //todo: Implement DAO method

            ctx.json(request);
            ctx.status(201);
        }

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
    public Request validateEntity(Context ctx) {
        return null;
    }
}
