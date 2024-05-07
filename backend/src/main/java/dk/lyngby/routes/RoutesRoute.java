package dk.lyngby.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class RoutesRoute {

    public EndpointGroup getRoutes(){
        return () -> {
            path("/route", () -> {

            });
        };
    }
}
