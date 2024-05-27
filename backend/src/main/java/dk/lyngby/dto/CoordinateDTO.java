package dk.lyngby.dto;

/**
 * @author MrJustMeDahl
 *
 * This dto is used for the response of the API call that returns the coordinates of a given location.
 */
public class CoordinateDTO {

    private Result[] results;

    public Result getResults() {
        return results[0];
    }

    public class Result{

        private double lon;
        private double lat;

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }
    }
}

