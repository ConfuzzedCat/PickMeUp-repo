package dk.lyngby.config;

import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.ExceptionHandler;
import dk.lyngby.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import lombok.NoArgsConstructor;
import io.javalin.http.Context;

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
        config.plugins.enableCors(cors -> {
            cors.add(it -> {
                it.reflectClientOrigin = true;
                it.allowCredentials = true;
                it.exposeHeader("Content-Type");
                it.exposeHeader("Authorization");
            });
        });
    }

    public static void startServer(Javalin app, int port) {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        app.before(ApplicationConfig::corsConfig);
        app.options("/*", ApplicationConfig::corsConfig);
        app.routes(routes.getRoutes(app));
        app.exception(ApiException.class, EXCEPTION_HANDLER::apiExceptionHandler);
        app.exception(Exception.class, EXCEPTION_HANDLER::exceptionHandler);
        app.start(port);
    }

    public static void corsConfig(Context ctx) {
        ctx.header("Access-Control-Allow-Origin", "*");
        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ctx.header("Access-Control-Allow-Credentials", "true");
    }


    public static void stopServer(Javalin app) {
        app.stop();
    }

    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }
}
