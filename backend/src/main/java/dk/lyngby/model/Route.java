package dk.lyngby.model;

public class Route {
    private String startLocation;
    private String endLocation;
    private String driverInfo;
    private double routeLength;
    private int timeInMinutes;

    // Constructor
    public Route(String startLocation, String endLocation, String driverInfo, double routeLength, int timeInMinutes) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverInfo = driverInfo;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
    }

    // Getters and setters
    public String getStartLocation() {

        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(double routeLength) {
        this.routeLength = routeLength;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    // toString method to represent Route object as a string

    @Override
    public String toString() {
        return "Route{" +
                "startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", driverInfo='" + driverInfo + '\'' +
                ", routeLength=" + routeLength +
                ", timeInMinutes=" + timeInMinutes +
                '}';
    }
}
