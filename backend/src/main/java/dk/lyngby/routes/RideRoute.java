package dk.lyngby.routes;


import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class RideRoute {

    private final RouteController routerController = new RouteController();

    protected EndpointGroup getRoutes() {

        return () -> {
            path("/rides", () -> {
                //  post("/", routerController::create)
                get("/", routerController::readAll);
                get("/{id}", routerController::read);
              //  put("/{id}", routerController::update);
              //  delete("/{id}", routerController::delete);
            });
        };
    }
}


