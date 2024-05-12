package dk.lyngby.model;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "departure_location", nullable = false)
    private String departureLocation;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    public Route(String departureLocation, String destination, LocalDateTime departureDateTime) {
        this.departureLocation = departureLocation;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
