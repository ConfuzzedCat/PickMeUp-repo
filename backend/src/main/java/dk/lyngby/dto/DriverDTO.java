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
String fullname; 
String password;
String Adress;



public DriverDTO(int id, String email, String fullname, String password, String Adress) {
this.id = id;
this.email = email;
this.fullname = fullname;
this.password = password;
this.Adress = Adress;
 }



public DriverDTO(Driver driver) {
this.id= driver.getId();
this.email = driver.getEmail();
this.fullname = driver.getFullname();
this.password = driver.getPassword();
}

}

