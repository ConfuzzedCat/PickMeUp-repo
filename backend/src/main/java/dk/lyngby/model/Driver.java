package dk.lyngby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "driver_license_number", nullable = false, unique = true)
    private String licenseNumber;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Route> routes = new HashSet<>();

    public Driver(String driverName, String licenseNumber) {
        this.driverName = driverName;
        this.licenseNumber = licenseNumber;
    }

    public void setRoutes(Set<Route> routes) {
        if(routes != null) {
            this.routes = routes;
            for (Route route : routes) {
                route.setDriver(this);
            }
        }
    }

    public void addRoute(Route route) {
        if (route != null) {
            this.routes.add(route);
            route.setDriver(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(licenseNumber, driver.licenseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseNumber);
    }
}
