package dk.lyngby.routes;

import dk.lyngby.controller.impl.ReviewController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class ReviewRoute {
    private final ReviewController rc = new ReviewController();

    public EndpointGroup getRoutes(){
        return () -> {
            path("/reviews", () -> {
                post("/", rc::create);
            });
        };
    }
}
