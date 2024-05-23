package dk.lyngby.model;



/**
 * This class is a mockup for a user that is needed for testing and implementation of US-#33-task-#83
 * @Author TobiasTonndorff
 * this class can be deleted once a real User class has been implemented

 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "userMock")
@Getter
@NoArgsConstructor

public class UserMock implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Basic(optional = false)
    @Column(name = "email", length = 25, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany
    @JsonIgnore
    private List<Route> rides = new ArrayList<>();

    @OneToMany(mappedBy = "requestSender")
    @JsonIgnore
    private List<RideRequest> outgoingRideRequests = new ArrayList<>();

    @OneToMany(mappedBy = "requestReceiver")
    @JsonIgnore
    private List<RideRequest> incomingRideRequests = new ArrayList<>();

    public UserMock(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addRide(Route newRide){
        rides.add(newRide);
    }

    public boolean hasRiddenRoute(Route route) {
        return rides.contains(route);
    }

    public void addOutgoingRideRequest(RideRequest rideRequest) {
        outgoingRideRequests.add(rideRequest);
    }

    public void addIncomingRideRequest(RideRequest rideRequest) {
        incomingRideRequests.add(rideRequest);
    }
}
