package dk.lyngby.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    private Driver driver;

    private int startPostalCode;
    private int endPostalCode;

    @Column(name = "startLocation", nullable = false)
    private String startLocation;

    @Column(name = "endLocation", nullable = false)
    private String endLocation;

    private double routeLength;

    private int timeInMinutes;

    private boolean handicapAvailability;
    private int passengerAmount;
    private int carSize;
    
    @Column(name = "departure_date_time", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime departureTime;
    @JsonIgnore
    @OneToMany(mappedBy = "ride")
    private List<RideRequest> rideRequests;

    public Route(Driver driver, int startPostalCode, int endPostalCode, String startLocation, String endLocation,
                 double routeLength, int timeInMinutes, boolean handicapAvailability, int passengerAmount, int carSize,
                 LocalDateTime departureTime) {
        this.driver = driver;
        this.startPostalCode = startPostalCode;
        this.endPostalCode = endPostalCode;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.handicapAvailability = handicapAvailability;
        this.passengerAmount = passengerAmount;
        this.carSize = carSize;
        this.departureTime = departureTime;
        this.rideRequests = new ArrayList<>();
    }

    public Route(int startPostalCode, int endPostalCode, String startLocation, String endLocation, double routeLength, int timeInMinutes, boolean handicapAvailability, int passengerAmount, int carSize, LocalDateTime departureTime) {
        this.startPostalCode = startPostalCode;
        this.endPostalCode = endPostalCode;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.handicapAvailability = handicapAvailability;
        this.passengerAmount = passengerAmount;
        this.carSize = carSize;
        this.departureTime = departureTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startLocation, endLocation, driver, routeLength, timeInMinutes, handicapAvailability, passengerAmount, carSize, departureTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(driver.getId(), route.getDriver().getId()) && Double.compare(routeLength, route.routeLength) == 0 && timeInMinutes == route.timeInMinutes && handicapAvailability == route.handicapAvailability && passengerAmount == route.passengerAmount && carSize == route.carSize && Objects.equals(startLocation, route.startLocation) && Objects.equals(endLocation, route.endLocation) && Objects.equals(departureTime, route.departureTime);
    }

    public void addRideRequest(RideRequest newRideRequest){
        rideRequests.add(newRideRequest);
    }
}
