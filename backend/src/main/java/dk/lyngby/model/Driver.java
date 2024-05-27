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

    @Column(name = "email")
    String email;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "password")
    String password;

    @Column(name = "address")
    String address;

    @Lob()
    private byte[] drivingLicense; // Billeddata for kørekort

    @Lob
    private byte[] studentCard; // Billeddata for studiekort

    @Column(name = "driver_license_number", nullable = false, unique = true)
    private String licenseNumber;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Route> routes = new HashSet<>();

    public Driver(String email, String driverName, String password, String address, byte[] drivingLicense, byte[] studentCard, String licenseNumber) {
        this.email = email;
        this.driverName = driverName;
        this.licenseNumber = licenseNumber;
        this.password = password;
        this.address = address;
        this.drivingLicense = drivingLicense;
        this.studentCard = studentCard;
    }

    public Driver(String email, String driverName, String password, String address, String licenseNumber, Set<Route> routes) {
        this.email = email;
        this.driverName = driverName;
        this.password = password;
        this.address = address;
        this.licenseNumber = licenseNumber;
        this.routes = routes;
    }

    public Driver(UserMock driver, String licenseNumber){
        this.driverName = driver.getFirstName() + " " + driver.getLastName();
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
