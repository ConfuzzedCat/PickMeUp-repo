package dk.lyngby.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int startPostalCode;
    private String startLocation;
    private int endPostalCode;
    private String endLocation;

    public Route(int startPostalCode, String startLocation, int endPostalCode, String endLocation) {
        this.startPostalCode = startPostalCode;
        this.startLocation = startLocation;
        this.endPostalCode = endPostalCode;
        this.endLocation = endLocation;
    }

    //private Driver driver;


}
