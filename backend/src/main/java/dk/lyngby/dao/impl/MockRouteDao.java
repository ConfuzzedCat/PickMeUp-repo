package dk.lyngby.dao.impl;

import dk.lyngby.config.MockDatabase;
import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Route;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MockRouteDao implements IDao {
    private MockDatabase mockDatabase;

    // Constructor
    public MockRouteDao(MockDatabase mockDatabase) {
        this.mockDatabase = mockDatabase;
    }

    public static MockRouteDao getInstance(EntityManagerFactory emf) {
        return null;
    }

    // Method to read all routes from the database

    @Override
    public Route read(Object primaryKey) throws ApiException {
        if (!(primaryKey instanceof Integer)) {
            throw new ApiException(400, "Primary key must be an integer");
        }

        int index = (int) primaryKey;
        List<Route> routes = mockDatabase.getAllRoutes();

        if (index < 0 || index >= routes.size()) {
            throw new ApiException(404, "Route not found with index: " + index);
        }

        return routes.get(index);
    }

    // Method to read all routes from the database

    public List<Route> readAll() {
        return mockDatabase.getAllRoutes();
    }

    //not used for this US
    @Override
    public Object create(Object o) {
        return null;
    }

    //not used for this US
    @Override
    public Object update(Object o, Object o2) {
        return null;
    }

    //not used for this US
    @Override
    public void delete(Object o) {

    }

    @Override
    public boolean validatePrimaryKey(Object o) {
        return false;

    }

}
