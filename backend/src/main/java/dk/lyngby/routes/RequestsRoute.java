package dk.lyngby.routes;

import dk.lyngby.controller.impl.RideRequestController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * This endpointgroup contains endpoints related to Requests
 * @author pelle112112
 */
public class RequestsRoute {

    private final RideRequestController rc = new RideRequestController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/requests", () -> {
                get("/outgoing/{userid}", rc::getAllOutgoingRequestsByUserId);
                get("/incoming/{userid}", rc::getAllIncomingRequestsByUserId);
                post("/requests", rc::create);
            });
        };
    }
}
