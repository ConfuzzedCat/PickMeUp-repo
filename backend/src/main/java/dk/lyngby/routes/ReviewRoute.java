package dk.lyngby.routes;

import dk.lyngby.controller.impl.ReviewController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ReviewRoute {
    private final ReviewController rc = new ReviewController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/reviews", () -> {
                get("/", rc::readAll);
                post("/", rc::create);
                get("/{id}", rc::read);
                get("/driver/{id}", rc::readAllByDriver);
                put("/{id}", rc::update);
                delete("/{id}", rc::delete);
            });
        };
    }
}
