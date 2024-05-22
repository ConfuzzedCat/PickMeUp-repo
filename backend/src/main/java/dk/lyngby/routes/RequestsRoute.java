package dk.lyngby.routes;

import dk.lyngby.controller.impl.RideRequestController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

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
                get("/{userid}", rc::getAllRequestsByUserId);
                post("/requests", rc::create);
            });
        };
    }
}
