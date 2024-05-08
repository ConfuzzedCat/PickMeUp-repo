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
    public Route read(Object o) throws ApiException {
        return null;
    }

    // Method to read all routes from the database
    @Override
    public List readAll() {
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
