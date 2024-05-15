package dk.lyngby.dto;


import dk.lyngby.model.Driver;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString


public class DriverDTO {

int id; 

String email;
String fullName;
String password;
String address;

byte[] drivingLicense; // Billeddata for kørekort
byte[] studentCard; // Billeddata for studiekort

public DriverDTO(int id, String email, String fullName, String password, String address, byte[] drivingLicense, byte[] studentCard) {
this.id = id;
this.email = email;
this.fullName = fullName;
this.password = password;
this.address = address;
this.drivingLicense = drivingLicense;
this.studentCard = studentCard;
 }



public DriverDTO(Driver driver) {
this.id= driver.getId();
this.email = driver.getEmail();
this.fullName = driver.getFullName();
this.password = driver.getPassword();
this.address = driver.getAddress();
this.drivingLicense = driver.getDrivingLicense();
this.studentCard = driver.getStudentCard();
}

}

