package dk.lyngby.controller.impl;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.dto.RouteDto;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Route;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RouteController implements IController<Route, Long> {

    private final RouteDao dao;

    public RouteController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = RouteDao.getInstance(emf);
    }

    @Override
    public void read(Context ctx) throws ApiException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Route route = dao.read(id);
        RouteDto routeDto = new RouteDto(route);
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDto.class);
    }

    @Override
    public void readAll(Context ctx) {
        List<Route> routes = dao.readAll();
        List<RouteDto> routeDtos = RouteDto.toRouteDTOList(routes);
        ctx.res().setStatus(200);
        ctx.json(routeDtos, RouteDto.class);
    }

    @Override
    public void create(Context ctx) {
        Route jsonRequest = ctx.bodyAsClass(Route.class);
        Route route = dao.create(jsonRequest);
        RouteDto routeDto = new RouteDto(route);
        ctx.res().setStatus(201);
        ctx.json(routeDto, RouteDto.class);
    }

    @Override
    public void update(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Route update = dao.update(id, validateEntity(ctx));
        RouteDto routeDto = new RouteDto(update);
        ctx.res().setStatus(200);
        ctx.json(routeDto, Route.class);
    }

    @Override
    public void delete(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        dao.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Long aLong) {
        return dao.validatePrimaryKey(aLong);
    }

    @Override
    public Route validateEntity(Context ctx) {
        return ctx.bodyValidator(Route.class)
                .check(r -> r.getDepartureLocation() != null && !r.getDepartureLocation().isEmpty(), "Departure location must be set")
                .check(r -> r.getDestination() != null && !r.getDestination().isEmpty(), "Destination must be set")
                .check(r -> r.getDepartureDateTime() != null, "Departure date and time must be set")
                .get();
    }
}
