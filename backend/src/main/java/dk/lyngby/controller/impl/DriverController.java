package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.DriverDao;
import dk.lyngby.dto.DriverDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Driver;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DriverController {

    DriverDao dao = new DriverDao();


    public Handler getById(Context ctx) {
        return Context -> {
            ctx.json(dao.getById(Integer.parseInt(ctx.pathParam("id"))));
        };
    }

    public Handler getalldDrivers(Context ctx) {
        return Context -> {
            List<Driver> drivers = dao.getalldDrivers();
            List<DriverDTO> driverDTOs = drivers.stream().map(DriverDTO::new).collect(Collectors.toList());
            ctx.json(driverDTOs);
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
        };
    }

    public Handler delete(Context ctx, int id) {
        return context -> { 
            dao.delete(id);
            context.status(202);
        };
    }
     

     

}
