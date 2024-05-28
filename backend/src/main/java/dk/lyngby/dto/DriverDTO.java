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

byte[] licenseImage; // Billeddata for kørekort
byte[] studentImage; // Billeddata for studiekort

public DriverDTO(int id, String email, String fullName, String password, String address, byte[] licenseImage, byte[] studentImage) {
this.id = id;
this.email = email;
this.fullName = fullName;
this.password = password;
this.address = address;
this.licenseImage = licenseImage;
this.studentImage = studentImage;
 }



public DriverDTO(Driver driver) {
this.id= driver.getId();
this.email = driver.getEmail();
this.fullName = driver.getFullName();
this.password = driver.getPassword();
this.address = driver.getAddress();
this.licenseImage = driver.getDrivingLicense();
this.studentImage = driver.getStudentCard();
}

}

