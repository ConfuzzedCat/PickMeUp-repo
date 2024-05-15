package dk.lyngby.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    String fullname;

    @Column(name = "password") 
    String password;

    @Column(name = "Address")
    String Address;

    public Driver(String email, String fullname, String password, String Address) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.Address = Address;
    }

    public Driver(int id, String email, String fullname, String password, String Address) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.Address = Address;
    }

    
}
