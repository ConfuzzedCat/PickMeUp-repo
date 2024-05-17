package dk.lyngby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ride_request")
@NamedQueries({
        @NamedQuery(name = "RideRequest.getAll", query = "SELECT r FROM RideRequest r"),
        @NamedQuery(name = "RideRequest.getRequestsForUser", query = "SELECT r FROM RideRequest r WHERE r.id.requestSenderID = :id")
})
public class RideRequest {

    @EmbeddedId
    private RideRequestID id;

    @ManyToOne
    @MapsId("requestSenderID")
    @JoinColumn(name = "request_sender")
    private UserMock requestSender;

    @ManyToOne
    @JoinColumn(name = "request_receiver")
    private UserMock requestReceiver;

    @MapsId("rideID")
    @ManyToOne
    private Route ride;

    private boolean accepted;

    public RideRequest(UserMock requestSender, UserMock requestReceiver, Route ride){
        id = new RideRequestID(requestSender.getId(), ride.getId());
        this.requestSender = requestSender;
        this.requestReceiver = requestReceiver;
        this.ride = ride;
        this.accepted = false;
        ride.addRideRequest(this);
        requestSender.addOutgoingRideRequest(this);
        requestReceiver.addIncomingRideRequest(this);
    }
}
