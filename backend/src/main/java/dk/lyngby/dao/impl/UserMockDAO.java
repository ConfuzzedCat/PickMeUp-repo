package dk.lyngby.dao.impl;

import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.UserMock;
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

    @Override
    public UserMock read(Integer d) throws ApiException {
        return null;
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
