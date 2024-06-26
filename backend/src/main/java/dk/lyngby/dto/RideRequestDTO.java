package dk.lyngby.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dk.lyngby.model.RideRequestID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author MrJustMeDahl
 * This class is used when receiving requests regarding the RideRequest entity, and for returning data as JSON in the response to those requests.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
