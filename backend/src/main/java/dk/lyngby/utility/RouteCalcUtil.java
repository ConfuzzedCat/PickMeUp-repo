package dk.lyngby.utility;

import com.google.gson.Gson;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.dto.CoordinateDTO;
import dk.lyngby.dto.DistanceDTO;
import dk.lyngby.exception.ApiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * @author MrJustMeDahl
 *
 * This utility class contains methods related to calculating geographical data for routes.
 */
public class RouteCalcUtil {

    private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private Gson gson = new Gson();

    /**
     * @author MrJustMeDahl
     *
     * This method takes an address, makes an api call to geoapify, which returns a response containing longitude and latitude of the entered address.
     *
     * @param location The address of the location you want converted to coordinates.
     * @return String of longitude and latitude separated by comma.
     * @throws ApiException If API call is unsuccessful.
     */
    public String getCoordinatesForAddress(String location) throws ApiException{
        // Location format: address,number,postal
        location = location.replaceAll(",", "%20");
        if(location.toLowerCase().contains("ø") || location.toLowerCase().contains("æ") || location.toLowerCase().contains("å")){
            location = location.replaceAll("ø", "%C3%B8");
            location = location.replaceAll("æ", "%C3%A6");
            location = location.replaceAll("å", "%C3%A5");
        }
        // new format: address%20number%20postal
        String key = "";
        try {
            key = ApplicationConfig.getProperty("geoapify.API.key");
        } catch (IOException e){
            throw new ApiException(500, "Something went wrong, try again later.");
        }
        String requestString = "https://api.geoapify.com/v1/geocode/search?text=" + location + "&format=json&apiKey=" + key;
        Request request = new Request.Builder()
                .url(requestString)
                .method("GET", null)
                .build();

        try {
            Response response = client.newCall(request).execute();
            CoordinateDTO dto = gson.fromJson(response.body().string(), CoordinateDTO.class);

            return dto.getResults().getLon() + "," + dto.getResults().getLat();
        } catch(IOException e){
            throw new ApiException(500, "Failed to convert address: " + location + " to coordinates. Something went wrong.");
        }
    }

    /**
     * This method takes the coordinates of 2 locations, makes an API call, which returns the driving distance between the 2 locations.
     *
     * @author MrJustMeDahl
     * @param startLocationCoords Coordinates representing where the user is.
     * @param routeCoords Coordinates representing where the route starts.
     * @return The driving distance between the 2 locations.
     * @throws ApiException If API call is unsuccessful.
     */
    public double findDistanceBetweenTwoLocations(String startLocationCoords, String routeCoords) throws ApiException {
        String key = "";
        try {
            key = ApplicationConfig.getProperty("geoapify.API.key");
        } catch (IOException e){
            throw new ApiException(500, "Something went wrong, try again later.");
        }
        String requestString = "https://api.geoapify.com/v1/routing?waypoints=" + startLocationCoords + "|" + routeCoords + "&mode=drive&apiKey=" + key;
        Request request = new Request.Builder()
                .url(requestString)
                .method("GET", null)
                .build();

        try {
            Response response = client.newCall(request).execute();
            DistanceDTO distanceDTO = gson.fromJson(response.body().string(), DistanceDTO.class);

            return distanceDTO.getFeatures().getProperties().getDistance();
        } catch (IOException e){
            throw new ApiException(500, "Failed to retrieve distance between the 2 locations. Something went wrong");
        }
    }
}
