package dk.lyngby.dto;

/**
 * @author MrJustMeDahl
 *
 * This dto is used for the response of the API call that returns the driving distance between 2 locations.
 */
public class DistanceDTO {

    private Feature[] features;

    public Feature getFeatures() {
        return features[0];
    }

    public class Feature {

        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public class Properties{

            double distance;

            public double getDistance() {
                return distance;
            }
        }
    }

}
