package dk.lyngby.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Route {
    private String startLocation;
    private String endLocation;
    private int driverId;
    private double routeLength;
    private int timeInMinutes;
    LocalDateTime departureTime;

    // Constructor
    public Route(String startLocation, String endLocation, int driverInfo, double routeLength, int timeInMinutes, LocalDateTime departureTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverId = driverInfo;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.departureTime = departureTime;
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

    public int getDriverInfo() {
        return driverId;
    }

    public void setDriverInfo(int driverInfo) {
        this.driverId = driverInfo;
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
                ", driverInfo='" + driverId + '\'' +
                ", routeLength=" + routeLength +
                ", timeInMinutes=" + timeInMinutes +
                '}';
    }
}
