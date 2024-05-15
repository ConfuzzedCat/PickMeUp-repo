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
@Table(name = "driver")

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

    @Column(name = "Adress")
    String Adress;

    public Driver(String email, String fullname, String password, String Adress) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.Adress = Adress;
    }

    public Driver(int id, String email, String fullname, String password, String Adress) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.Adress = Adress;
    }

    
}
