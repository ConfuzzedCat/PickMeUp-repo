package dk.lyngby.controller.impl;

import dk.lyngby.Model.Route;
import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * @author MrJustMeDahl
 * This controller is related to the route endpoints.
 */
public class RouteController {

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
    }
}
