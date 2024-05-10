package dk.lyngby.routes;

import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * @author MrJustMeDahl
 * This endpointgroup contains endpoints related to routes.
 */
public class RoutesRoute {

    private final RouteController rc = new RouteController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/rides", () -> {
                get("/", rc::readAll);
                get("/{id}", rc::read);
                post("/available_routes", rc::getListOfRoutesClosestToStart);
                post("/search", rc::searchFilters);
            });
        };
    }
}
