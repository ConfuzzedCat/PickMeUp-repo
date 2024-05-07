package dk.lyngby.dto;

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
