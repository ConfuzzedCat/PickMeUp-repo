package dk.lyngby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestID implements Serializable {

    private int requestSenderID;
    private int rideID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideRequestID that = (RideRequestID) o;
        return Objects.equals(requestSenderID, that.requestSenderID) &&
                Objects.equals(rideID, that.rideID);
    }
}