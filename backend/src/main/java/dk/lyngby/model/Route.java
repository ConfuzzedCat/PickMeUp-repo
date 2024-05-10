package dk.lyngby.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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
    private boolean handicapAvailability;
    private int passengerAmount;
    private int carSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime departureTime;

    // Constructor
    public Route(String startLocation, String endLocation, int driverId, double routeLength, int timeInMinutes, boolean handicapAvailability, int passengerAmount, int carSize, LocalDateTime departureTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverId = driverId;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.handicapAvailability = handicapAvailability;
        this.passengerAmount = passengerAmount;
        this.carSize = carSize;
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && driverId == route.driverId && Double.compare(routeLength, route.routeLength) == 0 && timeInMinutes == route.timeInMinutes && handicapAvailability == route.handicapAvailability && passengerAmount == route.passengerAmount && carSize == route.carSize && Objects.equals(startLocation, route.startLocation) && Objects.equals(endLocation, route.endLocation) && Objects.equals(departureTime, route.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startLocation, endLocation, driverId, routeLength, timeInMinutes, handicapAvailability, passengerAmount, carSize, departureTime);
    }
}
