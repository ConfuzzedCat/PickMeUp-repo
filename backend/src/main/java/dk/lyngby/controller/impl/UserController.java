package dk.lyngby.controller.impl;


import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.UserMockDAO;
import dk.lyngby.dto.UserMockDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.UserMock;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;



/**
 * @Author Carsten Juhl
 * This is a controller for the User entity.
 */
public class UserController implements IController<UserMock, Integer> {

    private UserMockDAO dao;

    public UserController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = UserMockDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) throws ApiException {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // entity
        UserMock user = dao.readInfo(id);
        // dto
        UserMockDTO userDTO = new UserMockDTO(user);
        // response
        ctx.res().setStatus(200);
        ctx.json(userDTO, UserMockDTO.class);
    }

    @Override
    public void readAll(Context ctx) throws ApiException {

    }

    @Override
    public void create(Context ctx) throws ApiException {

    }

    @Override
    public void update(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }

    @Override
    public UserMock validateEntity(Context ctx) {
        return null;
    }
}
