package dk.lyngby.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverRideRequestDTO {

    private int requestSenderID;
    private int rideID;
    private boolean accepted;

}
