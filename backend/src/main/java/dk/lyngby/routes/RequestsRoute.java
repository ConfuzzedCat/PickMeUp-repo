package dk.lyngby.routes;

import dk.lyngby.controller.impl.RideRequestController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import dk.lyngby.controller.impl.DriverRideRequestController;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * This endpointgroup contains endpoints related to Requests
 * @author pelle112112
 */
public class RequestsRoute {

    private final RideRequestController rc = new RideRequestController();
    private final DriverRideRequestController drc = new DriverRideRequestController();


    public EndpointGroup getRoutes(){
        return () -> {
            path("/requests", () -> {
                get("/outgoing/{userid}", rc::getAllOutgoingRequestsByUserId);
                get("/incoming/{userid}", rc::getAllIncomingRequestsByUserId);
                post("/requests", rc::create);
                post("/accept", drc::acceptRideRequest);
                post("/decline", drc::declineRideRequest);
            });
        };
    }
}
