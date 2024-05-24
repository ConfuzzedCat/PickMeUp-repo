package dk.lyngby.dao.impl;

import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * @author Dimitra Siskou
 * This class is a mock DAO for the User entity.
 TODO:replace the mock DAO with the real User DAO
 */
public class UserMockDAO implements IDao<UserMock, String> {

    private static UserMockDAO instance;
    private static EntityManagerFactory emf;

    public static UserMockDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserMockDAO();
        }
        return instance;
    }
    // TODO: Clarification needed, is the id meant to be a string or integer? Remove the proxy read method below when fixed. from @ConfuzzedCat
    @Override
    //public UserMock read(Integer d) throws ApiException { // this is what it was, need to make it a string to be able to compile.
    public UserMock read(String d) throws ApiException {
        UserMock found;
        try (EntityManager em = emf.createEntityManager()) {
            found = em.find(UserMock.class, d);
            found.getOutgoingRideRequests().size();
        }
        return found;
    }
    
    public UserMock read(Integer d) throws ApiException {
        return read(d.toString());
    }

    public UserMock readInfo(Integer d) throws ApiException {
        UserMock info;
        try (var em = emf.createEntityManager()) {
            info = em.find(UserMock.class, d);
            info.getFirstName();
            info.getLastName();
            info.getEmail();
        }
            return info;

    }

    @Override
    public List<UserMock> readAll() {
        return null;
    }

    @Override
    public UserMock create(UserMock userMock) {
        return null;
    }

    @Override
    public UserMock update(String s, UserMock userMock) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    public boolean validatePrimaryKey(String email) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            UserMock user = em.find(UserMock.class, email);
            em.getTransaction().commit();
            return user != null;
        }
    }
}
