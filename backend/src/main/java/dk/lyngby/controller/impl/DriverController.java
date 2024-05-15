package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.DriverDao;
import dk.lyngby.dto.DriverDTO;
import dk.lyngby.dto.RouteDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DriverController implements IController<Driver, Integer>{

    DriverDao dao = new DriverDao();

    public void readAll(Context ctx) {
        ctx.json(dao.readAll());
    }


    public void read(Context ctx) throws ApiException {
         // request
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // entity
        Driver driver = dao.read(id);
        // dto
        DriverDTO driverDto = new DriverDTO(driver); 
        // response
        ctx.res().setStatus(200);
        ctx.json(driverDto, DriverDTO.class);
    }


    public Handler getById() {
        return ctx -> {
            ctx.json(dao.getById(Integer.parseInt(ctx.pathParam("id"))));
        };
    }

    public void create(Context ctx){
        Driver driver = ctx.bodyAsClass(Driver.class);
        ctx.status(201);
    }

    public Handler update(Context ctx, int id) {
        return context -> {
            Driver driver = context.bodyAsClass(Driver.class);
            dao.update(id, driver);
            context.status(202);
        };
    }

    public Handler delete(Context ctx, int id) {
        return context -> { 
            dao.delete(id);
            context.status(202);
        };
    }
     

     

}
