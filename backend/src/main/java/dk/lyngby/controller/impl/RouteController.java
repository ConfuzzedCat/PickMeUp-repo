package dk.lyngby.controller.impl;

import dk.lyngby.Model.Route;
import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class RouteController {

    private RouteCalcUtil routeUtil = new RouteCalcUtil();

    public void getListOfRoutesClosestToStart(Context ctx) throws ApiException{
        // Hiv start og slut ud af context.
        RequestBody requestBody = ctx.bodyAsClass(RequestBody.class);
        String startLocationCoords = routeUtil.getCoordinatesForAddress(requestBody.getStartLocation());
        // Hent driver routes i DB, som har samme slutpunkt.
        // Sammenlign distancen mellem startpunktet for brugeren og startpunktet for routen, via geoapify.
        Map<Route, Double> chosenRoutes = new HashMap<Route, Double>();
        for(Route r: routes){
            String routeCoords = routeUtil.getCoordinatesForAddress(r.getStartLocation());
            double distance = routeUtil.findDistanceBetweenTwoLocations(startLocationCoords, routeCoords);
            if(distance >= 0) {
                chosenRoutes.put(r, distance);
            }
        }
        // Filtrér routes fra som er urealistike / ikke passer til filtrering.
        // Sortér routes sådan at de routes som er tættest på brugerens start lokation bliver vist først.

    }

    private static class RequestBody {

        private String startLocation;
        private String endLocation;

        private String getStartLocation(){
            return startLocation;
        }
    }
}
