package dk.lyngby.controller.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
import dk.lyngby.model.Route;
import dk.lyngby.dto.RouteDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MrJustMeDahl
 * This controller is related to the route endpoints.
 */
public class RouteController implements IController<Route, Integer> {


    private RouteDao dao;

    public RouteController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = RouteDao.getInstance(emf);
    }

        private RouteCalcUtil routeUtil = new RouteCalcUtil();


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
        String endAddress = requestBody.getEndLocation().substring(0, requestBody.getEndLocation().length()-5);
        List<Route> routes = dao.getPassengerRoutesWithFilter(endAddress, Integer.parseInt(endLocation[endLocation.length -1]), Integer.parseInt(startLocation[startLocation.length -1]));
        // Get the distance between the start location of the user and the start location of every given route, via external API call.
        Map<Route, Double> chosenRoutes = new HashMap<>();
        for(Route r: routes){
            String location = r.getStartLocation().replaceAll(" ", ",");
            location = location + ","+ r.getStartPostalCode();
            String routeCoords = routeUtil.getCoordinatesForAddress(location);
            double distance = routeUtil.findDistanceBetweenTwoLocations(startLocationCoords, routeCoords);
            if(distance >= 0) {
                chosenRoutes.put(r, distance);
            }
        }

        // Filter routes that is so far away that it would be unreasonable to walk to the route's starting location.
        // Sort the routes so that the routes which is closest to the location of the user is shown first.

        ctx.json(sortedRoutes(chosenRoutes, 10000));
        ctx.status(200);

    }
    /**
     * This method returns a list of routes based on the users chosen max distance to startlocation from own address
     *
     * @param chosenRoutes,      is all the routes with the same endlocation as the user has chosen, within the users postal code
     * @param userInputDistance, is the max distance the user wants to travel to the startlocation
     * @return a Hashmap with the relevant routes for the user based on the max distance to startlocation from own address
     * @author Carsten Juhl
     */
    public static List<Route> sortedRoutes(Map<Route, Double> chosenRoutes, double userInputDistance) {
        // removes all routes with a distance greater than the user input
        chosenRoutes.entrySet().removeIf(entry -> entry.getValue() > userInputDistance);

        // Puts the sorted routes into a new Hashmap
        List<Route> sortedRoutes = chosenRoutes.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());

        return sortedRoutes;
    }

    private static class RequestBody {

        @JsonProperty
        private String startLocation;
        @Getter
        @JsonProperty
        private String endLocation;

        public RequestBody(String startLocation, String endLocation) {
            this.startLocation = startLocation;
            this.endLocation = endLocation;
        }

        public RequestBody() {
        }

        private String getStartLocation(){
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public void setEndLocation(String endLocation) {
            this.endLocation = endLocation;
        }
    }


    /**
     * Reads a single Route entity based on the ID provided in the request context,
     * converts it to a RouteDTO, and sends it back as a JSON response.
     *
     * @param ctx the Javalin context containing the request data and methods for response handling.
     * @throws ApiException if an error occurs during the process of reading the entity.
     * @author Deniz Sønnmez
     */
    @Override
    public void read(Context ctx) throws ApiException {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // entity
        Route route = dao.read(id);
        // dto
        RouteDTO routeDto = new RouteDTO(route);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDTO.class);
    }

    /**
     * Reads all Route entities, converts them to a list of RouteDTOs,
     * and sends the list back as a JSON response.
     *
     * @param ctx the Javalin context containing the request data and methods for response handling.
     * @author Deniz Sønnmez
     */
    @Override
    public void readAll(Context ctx) {
        // entity
        List<Route> routeList = dao.readAll();
        // dto
        List<RouteDTO> routeDTOList = RouteDTO.toDTOList(routeList);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDTOList, RouteDTO.class);
    }


    @Override
    public void create(Context ctx) {
        Route jsonRequest = ctx.bodyAsClass(Route.class);
        Route route = dao.create(jsonRequest);
        RouteDTO routeDto = new RouteDTO(route);
        ctx.res().setStatus(201);
        ctx.json(routeDto, RouteDTO.class);
    }

    @Override
    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        Route update = dao.update(id, validateEntity(ctx));
        RouteDTO routeDto = new RouteDTO(update);
        ctx.res().setStatus(200);
        ctx.json(routeDto, Route.class);
    }

    @Override
    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        dao.delete(id);
        ctx.res().setStatus(204);
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
        List<Route> routes = dao.searchFilters(jsonObject);

        List<RouteDTO> routeDto = RouteDTO.toDTOList(routes);
        // response
        ctx.res().setStatus(200);
        ctx.json(routeDto, RouteDTO.class);
    }
}

