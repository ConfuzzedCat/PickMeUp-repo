package dk.lyngby.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @JoinColumn(name = "driver_id", nullable = false)
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
    private LocalDateTime departureDateTime;

    public Route(Driver driver, int startPostalCode, int endPostalCode, String startLocation, String endLocation,
                 double routeLength, int timeInMinutes, boolean handicapAvailability, int passengerAmount, int carSize,
                 LocalDateTime departureDateTime) {
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
        this.departureDateTime = departureDateTime;
    }




}
