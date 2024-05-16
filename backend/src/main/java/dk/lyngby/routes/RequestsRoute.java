package dk.lyngby.routes;

import dk.lyngby.controller.impl.RequestController;
import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * @author pelle112112
 * This endpointgroup contains endpoints related to Requests
 */
public class RequestsRoute {

    private final RequestController rc = new RequestController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/requests", () -> {
                get("/{userid}", rc::getAllRequestsByUserId);
                post("/requests", rc::create);

            });
        };
    }
}
