package dk.lyngby.config;

import dk.lyngby.model.Route;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    private List<Route> routes;

    // Constructor
    public MockDatabase() {
        this.routes = new ArrayList<>();
        // You can add some mock data here if needed
        initializeMockData();
    }

    // Method to add a route to the database
    public void addRoute(Route route) {
        routes.add(route);
    }

    // Method to retrieve all routes from the database
    public List<Route> getAllRoutes() {
        return routes;
    }

    // Method to initialize mock data
    private void initializeMockData() {
        // Adding mock routes to our mock DB
        Route route1 = new Route("Start1", "End1", "Driver1", 10.5, 30);
        Route route2 = new Route("Start2", "End2", "Driver2", 8.2, 25);
        Route route3 = new Route("Start3", "End3", "Driver3", 15.0, 40);

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
