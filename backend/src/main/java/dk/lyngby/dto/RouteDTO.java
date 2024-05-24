package dk.lyngby.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.lyngby.model.Route;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Getter
public class RouteDTO {

    private int id;
    private int startPostalCode;
    private int endPostalCode;
    private String startLocation;
    private String endLocation;
    private int driverId;
    private double routeLength;
    private int timeInMinutes;
    private boolean handicapAvailability;
    private int passengerAmount;
    private int carSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime departureTime;

    // Default constructor needed by Jackson
    public RouteDTO() {}


    public RouteDTO(Route route) {
        this.id = route.getId();
        this.startPostalCode = route.getStartPostalCode();
        this.endPostalCode = route.getEndPostalCode();
        this.startLocation = route.getStartLocation();
        this.endLocation = route.getEndLocation();
        this.driverId = route.getDriver().getId();
        this.routeLength = route.getRouteLength();
        this.timeInMinutes = route.getTimeInMinutes();
        this.handicapAvailability = route.isHandicapAvailability();
        this.passengerAmount = route.getPassengerAmount();
        this.carSize = route.getCarSize();
        this.departureTime = route.getDepartureTime();

    }


    public static List<RouteDTO> toDTOList(List<Route> routeList) {
        return routeList.stream().map(RouteDTO::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteDTO routeDto)) return false;
        return driverId == routeDto.driverId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId);
    }
}