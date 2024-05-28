package dk.lyngby.routes;
import dk.lyngby.controller.impl.DriverController;
import dk.lyngby.controller.impl.RouteController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class DriverRoute {
    
    private final DriverController dc = new DriverController();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/drivers", () -> {
                get("/", dc::getallDrivers);
                get("/{id}", dc::getById);
                post("/signup", dc::create);
                put("/{id}", dc::update);
                delete("/{id}", dc::delete);
            });
        };
    }
}
