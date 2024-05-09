package dk.lyngby.controller.impl;

import dk.lyngby.Model.Route;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteControllerTest {
    private static RouteController routeController;
    private static HashMap<Route, Double> chosenRoute = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {

        HibernateConfig.setTest(true);
        routeController = new RouteController();


        chosenRoute.put(new Route("Route1"), 1000.00);
        chosenRoute.put(new Route("Route2"), 450.00);
        chosenRoute.put(new Route("Route3"), 260.00);
        chosenRoute.put(new Route("Route4"), 320.00);
        chosenRoute.put(new Route("Route5"), 117.00);
        chosenRoute.put(new Route("Route6"), 420.00);
        chosenRoute.put(new Route("Route7"), 260.00);
        chosenRoute.put(new Route("Route8"), 1500.00);

    }

    @AfterEach
    void tearDown() {
        chosenRoute.clear();
    }

    @Test
    void sortedRoutes() {
        List<Route> sortedRoutes = routeController.sortedRoutes(chosenRoute, 500);

        assertEquals("Route5",sortedRoutes.get(0).getStartLocation());
        assertEquals("Route2",sortedRoutes.get(sortedRoutes.size()-1).getStartLocation());
        assertEquals(6, sortedRoutes.size());
    }
}