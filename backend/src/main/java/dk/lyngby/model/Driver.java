package dk.lyngby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Setter
@Table(name = "Driver")

public class Driver {
    
    @Id
    @GeneratedValue
    int id;

    @Column(name = "email")
    String email;

    @Column(name = "fullname")
    String fullName;

    @Column(name = "password") 
    String password;

    @Column(name = "address")
    String address;

    @Lob()
    private byte[] drivingLicense; // Billeddata for kørekort

    @Lob
    private byte[] studentCard; // Billeddata for studiekort

    public Driver(String email, String fullName, String password, String address, byte[] drivingLicense, byte[] studentCard) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.address = address;
        this.drivingLicense = drivingLicense;
        this.studentCard = studentCard;
    }

    public Driver(int id, String email, String fullName, String password, String address) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.address = address;
    }

    
}
