package dk.lyngby.routes;

import dk.lyngby.controller.impl.HotelController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RideRoute {

    private final RouterController routerController = new HotelController();

    protected EndpointGroup getRoutes() {

        return () -> {
            path("/rides", () -> {
              //  post("/", routerController::create);
                get("/", routerController::readAll);
                get("/{id}", routerController::read);
              //  put("/{id}", routerController::update);
              //  delete("/{id}", routerController::delete);
            });
        };
    }
}


