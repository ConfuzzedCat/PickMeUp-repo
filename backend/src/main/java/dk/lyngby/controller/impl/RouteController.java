package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.exception.ApiException;
import io.javalin.http.Context;

public class RouteController {

    public void getListOfRoutesClosestToStart(Context ctx){
        // Hiv start og slut ud af context.
        // Hent driver routes i DB, som har samme slutpunkt.
        // Sammenlign distancen mellem startpunktet for brugeren og startpunktet for routen, via geoapify.
        // Filtrér routes fra som er urealistike / ikke passer til filtrering.
        // Sortér routes sådan at de routes som er tættest på brugerens start lokation bliver vist først.

    }
}
