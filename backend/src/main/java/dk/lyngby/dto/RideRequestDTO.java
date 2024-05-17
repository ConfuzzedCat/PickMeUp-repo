package dk.lyngby.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dk.lyngby.model.RideRequestID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideRequestDTO {

    @JsonProperty
    private RideRequestID ID;
    @JsonProperty
    private int rideRequestSenderID;
    @JsonProperty
    private int rideRequestReceiverID;
    @JsonProperty
    private int rideID;

    public RideRequestID getId() {
        return ID;
    }
}
