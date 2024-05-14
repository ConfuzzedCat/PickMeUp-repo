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
<<<<<<< HEAD
            path("/route", () -> {
                post("/available_routes", rc::getListOfRoutesClosestToStart);
=======
            path("/rides", () -> {
                get("/", rc::readAll);
                get("/{id}", rc::read);
                post("/available_routes", rc::getListOfRoutesClosestToStart);
                post("/search", rc::searchFilters);
>>>>>>> cfb957b38200def3b5c649896a501c6f7f6bc24c
            });
        };
    }
}
