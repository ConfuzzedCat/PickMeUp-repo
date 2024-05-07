package dk.lyngby.routes;

import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class RoutesRoute {

    private final RouteController rc = new RouteController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/route", () -> {
                get("/available_routes", rc::getListOfRoutesClosestToStart);
            });
        };
    }
}
