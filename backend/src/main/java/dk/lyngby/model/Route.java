package dk.lyngby.model;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Route {
    private String startLocation;
    private String endLocation;
    private int driverId;
    private double routeLength;
    private int timeInMinutes;
    LocalDateTime departureTime;

    // Constructor
    public Route(String startLocation, String endLocation, int driverId, double routeLength, int timeInMinutes, LocalDateTime departureTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverId = driverId;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.departureTime = departureTime;
    }
}
