package dk.lyngby.utility;

import com.google.gson.Gson;
import dk.lyngby.dto.CoordinateDTO;
import dk.lyngby.dto.DistanceDTO;
import dk.lyngby.exception.ApiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RouteCalcUtil {

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Gson gson = new Gson();

    public String getCoordinatesForAddress(String location) throws ApiException{
        String requestString = "https://api.geoapify.com/v1/geocode/search?text=" + location + "&format=json&apiKey=c205e64d77a04951959d6de10d7e5c2e";
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

    public double findDistanceBetweenTwoLocations(String startLocationCoords, String routeCoords) throws ApiException {
        String requestString = "https://api.geoapify.com/v1/routing?waypoints=" + startLocationCoords + "|" + routeCoords + "&mode=drive&apiKey=c205e64d77a04951959d6de10d7e5c2e";
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
