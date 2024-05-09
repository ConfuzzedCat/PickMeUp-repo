package dk.lyngby.config;

import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.ExceptionHandler;
import dk.lyngby.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import lombok.NoArgsConstructor;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
        app.routes(routes.getRoutes(app));
        app.exception(ApiException.class, EXCEPTION_HANDLER::apiExceptionHandler);
        app.exception(Exception.class, EXCEPTION_HANDLER::exceptionHandler);
        app.start(port);
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    public static String getProperty(String propName) throws IOException
    {
        String result = "";
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            result = model.getProperties().getProperty(propName);
        } catch (IOException | XmlPullParserException e){

        }
        return result;
    }
}
