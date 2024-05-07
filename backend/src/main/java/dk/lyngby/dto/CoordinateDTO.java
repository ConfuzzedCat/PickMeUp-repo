package dk.lyngby.dto;


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

