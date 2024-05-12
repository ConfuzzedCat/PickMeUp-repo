package dk.lyngby.util;

import dk.lyngby.exception.ApiException;
import dk.lyngby.utility.RouteCalcUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteCalcUtilTest {

    private RouteCalcUtil rcu = new RouteCalcUtil();

    @Test
    void getCoordinatesForAddressTest(){
        String address = "Firskovvej,18,2800";
        String addressDanish = "Nørgaardsvej,30,2800";
        try {
            String coordinates = rcu.getCoordinatesForAddress(address);
            assertEquals("55.770349,12.51404", coordinates);
            coordinates = rcu.getCoordinatesForAddress(addressDanish);
            assertEquals("55.770179,12.511735", coordinates);
        } catch (ApiException e){

        }
    }

    @Test
    void findDistanceBetweenTwoLocationsTest(){
        String startCoordinates = "55.770349,12.51404";
        String endCoordinates = "55.772515,12.505042";
        try{
            double distance = rcu.findDistanceBetweenTwoLocations(startCoordinates, endCoordinates);
            assertEquals(781, distance);
        } catch (ApiException e){

        }
    }

}
