package dk.lyngby.config;

import dk.lyngby.model.Route;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    private List<Route> routes;

    // Constructor
    public MockDatabase() {
        this.routes = new ArrayList<>();

        initializeMockData();
    }

    public void addRoute(Route route) {

        routes.add(route);
    }


    public List<Route> getAllRoutes() {

        return routes;
    }

    // Method to initialize mock data
    private void initializeMockData() {
        // Adding mock routes to our mock DB
        Route route1 = new Route("Start1", "End1", 1, 10.2, 30, true, 3, 5, LocalDateTime.of(2024, 5, 10, 8, 0));
        Route route2 = new Route("Start2", "End2", 2, 8.2, 25, false, 2, 3, LocalDateTime.of(2024, 5, 9, 8, 30));
        Route route3 = new Route("Start3", "End3", 3, 15.0, 40, true, 5, 7, LocalDateTime.of(2024, 5, 11, 9, 0));

        // Actually adding
        addRoute(route1);
        addRoute(route2);
        addRoute(route3);
    }

    // test the mock database
    public static void main(String[] args) {
        MockDatabase mockDatabase = new MockDatabase();

        // Retrieve all routes from the database
        List<Route> allRoutes = mockDatabase.getAllRoutes();

        // Print all routes
        for (Route route : allRoutes) {
            System.out.println(route);
        }
    }
}
