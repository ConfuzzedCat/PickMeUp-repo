package dk.lyngby.routes;

import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.ExceptionHandler;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final ExceptionHandler exceptionController = new ExceptionHandler();
    private final RoutesRoute routesRoute = new RoutesRoute();
    private final RequestsRoute requestsRoute = new RequestsRoute();
    private final UserRoute userRoute = new UserRoute();

    private final ReviewRoute reviewRoute = new ReviewRoute();
    private int count = 0;

    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    private void requestInfoHandler(Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
        ctx.attribute("requestInfo", requestInfo);
    }

    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.before(this::requestInfoHandler);

            app.routes(() -> {
                path("/", routesRoute.getRoutes());
                path("/", requestsRoute.getRoutes());
                path("/", userRoute.getInfo());
                path("/", reviewRoute.getRoutes());
            });

            app.after(ctx -> LOGGER.info(" Request {} - {} was handled with status code {}", count++, ctx.attribute("requestInfo"), ctx.status()));

            app.exception(ApiException.class, exceptionController::apiExceptionHandler);
            app.exception(Exception.class, exceptionController::exceptionHandler);
        };
    }
}
