package dk.lyngby.routes;

import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DriverRoute {

    private final RouteController routeController = new RouteController();

    protected EndpointGroup getRoutes() {

        return () -> {
            path("/hotels", () -> {
                post("/", routeController::create);
                get("/", routeController::readAll);
                get("/{id}", routeController::read);
                put("/{id}", routeController::update);
                delete("/{id}", routeController::delete);
            });
        };
    }
}
