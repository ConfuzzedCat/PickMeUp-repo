package dk.lyngby.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String startLocation;
    private String endLocation;
    private int driverId;
    private double routeLength;
    private int timeInMinutes;
    private LocalTime departureTime;

    // Constructor
    public Route(String startLocation, String endLocation, int driverId, double routeLength, int timeInMinutes, LocalTime departureTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverId = driverId;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.departureTime = departureTime;
    }
}
