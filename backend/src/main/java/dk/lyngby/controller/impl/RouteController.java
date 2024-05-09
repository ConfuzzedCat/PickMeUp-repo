package dk.lyngby.controller.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.MockRouteDao;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.dto.RouteDto;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Route;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.List;


public class RouteController implements IController<Route, Integer> {

    
    private final MockRouteDao dao;
    //database access object for the Route entity
    private final RouteDao routeDao;

    public RouteController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = MockRouteDao.getInstance(emf);
        this.routeDao = RouteDao.getInstance(emf);
    }

    @Override
    public void read(Context ctx) throws ApiException
    {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // entity
        Route route = dao.read(id);
        // dto
        RouteDto routeDto = new RouteDto(route);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDto.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<Route> routeList = dao.readAll();
        // dto
        List<RouteDto> routeDtoList = RouteDto.toDTOList(routeList);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDtoList, RouteDto.class);
    }


    @Override
    public void create(Context ctx) {

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
    public Route validateEntity(Context ctx) {
        return null;
    }

    //controller for the searchFilters in RouteDao
    public void searchFilters(Context ctx) {
        String requestBody = ctx.body();

        JsonObject jsonObject = new Gson().fromJson(requestBody, JsonObject.class);
        String startLocation = jsonObject.get("startLocation").getAsString();
        String endLocation = jsonObject.get("endLocation").getAsString();
        int driverId = jsonObject.get("driverId").getAsInt();
        double routeLength = jsonObject.get("routeLength").getAsDouble();
        int timeInMinutes = jsonObject.get("timeInMinutes").getAsInt();
        boolean handicapAvailability = jsonObject.get("handicapAvailability").getAsBoolean();
        int passengerAmount = jsonObject.get("passengerAmount").getAsInt();
        int carSize = jsonObject.get("carSize").getAsInt();
        LocalTime departureTime = LocalTime.parse(jsonObject.get("departureTime").getAsString());

        // entity
        List<Route> routes = routeDao.searchFilters(startLocation, endLocation,
                driverId, routeLength, timeInMinutes, handicapAvailability,
                passengerAmount, carSize,departureTime);

        List<RouteDto> routeDto = RouteDto.toDTOList(routes);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDto.class);

    }

}
