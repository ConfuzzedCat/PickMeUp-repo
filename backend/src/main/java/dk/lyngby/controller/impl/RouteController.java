package dk.lyngby.controller.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.impl.MockRouteDao;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.dto.RouteDto;
import dk.lyngby.Model.Route;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
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
    public void searchFilters(Context ctx) throws Exception{
        String requestBody = ctx.body();

        JsonObject jsonObject = new Gson().fromJson(requestBody, JsonObject.class);

        // Extract the fields dynamically
        List<Route> routes = routeDao.searchFilters(jsonObject);

        List<RouteDto> routeDto = RouteDto.toDTOList(routes);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDto.class);
    }

    private RouteCalcUtil routeUtil = new RouteCalcUtil();
    private RouteDao dao = RouteDao.getInstance();

    /**
     * This method is handling the context of /route/available_routes endpoint.
     * It returns a list of routes that fits the user's end location sorted by distance to the user's location.
     *
     * @author MrJustMeDahl
     * @param ctx
     * @throws ApiException
     */
    public void getListOfRoutesClosestToStart(Context ctx) throws ApiException{
        // Get what we need from the context object.
        RequestBody requestBody = ctx.bodyAsClass(RequestBody.class);
        String startLocationCoords = routeUtil.getCoordinatesForAddress(requestBody.getStartLocation());
        // Get driver routes from the DB which fits the user's end location and is starting within reasonable distance from the user's start location.
        String[] startLocation = requestBody.getStartLocation().split(",");
        String[] endLocation = requestBody.getEndLocation().split(",");
        List<Route> routes = dao.getPassengerRoutesWithFilter(requestBody.getEndLocation(), Integer.parseInt(startLocation[startLocation.length -1]), Integer.parseInt(endLocation[endLocation.length -1]));
        // Get the distance between the start location of the user and the start location of every given route, via external API call.
        Map<Route, Double> chosenRoutes = new HashMap<>();
        for(Route r: routes){
            String routeCoords = routeUtil.getCoordinatesForAddress(r.getStartLocation());
            double distance = routeUtil.findDistanceBetweenTwoLocations(startLocationCoords, routeCoords);
            if(distance >= 0) {
                chosenRoutes.put(r, distance);
            }
        }

        // Filter routes that is so far away that it would be unreasonable to walk to the route's starting location.
        // Sort the routes so that the routes which is closest to the location of the user is shown first.

    }
    private static class RequestBody {

        private String startLocation;
        private String endLocation;

        private String getStartLocation(){
            return startLocation;
        }

        public String getEndLocation() {
            return endLocation;
        }
    }
}


