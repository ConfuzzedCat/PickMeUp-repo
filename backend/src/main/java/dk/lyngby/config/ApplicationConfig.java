package dk.lyngby.config;

import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.ExceptionHandler;
import dk.lyngby.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig {

    private static final ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler();

    public static void configuration(JavalinConfig config) {
        config.routing.contextPath = "/api/v1"; // base path for all routes
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.plugins.register(new RouteOverviewPlugin("/")); // enables route overview at /
    }

    public static void startServer(Javalin app, int port) {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        app.routes(routes.getRoutes(app));
        app.exception(ApiException.class, EXCEPTION_HANDLER::apiExceptionHandler);
        app.exception(Exception.class, EXCEPTION_HANDLER::exceptionHandler);
        app.start(port);
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }
}
