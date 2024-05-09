package dk.lyngby.controller.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.lyngby.Model.Route;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * @author MrJustMeDahl
 * This controller is related to the route endpoints.
 */
public class RouteController {

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

        ctx.json(routes);

        // Filter routes that is so far away that it would be unreasonable to walk to the route's starting location.
        // Sort the routes so that the routes which is closest to the location of the user is shown first.

    }

    private static class RequestBody {

        @JsonProperty
        private String startLocation;
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

        public String getEndLocation() {
            return endLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public void setEndLocation(String endLocation) {
            this.endLocation = endLocation;
        }
    }
}
