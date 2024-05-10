package dk.lyngby.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.lyngby.model.Route;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
@Getter
public class RouteDto {

    private String startLocation;
    private String endLocation;
    private int driverId;
    private double routeLength;
    private int timeInMinutes;
    private LocalTime departureTime;
    private boolean handicapAvailability;
    private int passengerAmount;
    private int carSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime departureTime;

    public RouteDto(String startLocation, String endLocation, int driverId, double routeLength, int timeInMinutes, boolean handicapAvailability, int passengerAmount, int carSize, LocalDateTime departureTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.driverId = driverId;
        this.routeLength = routeLength;
        this.timeInMinutes = timeInMinutes;
        this.handicapAvailability = handicapAvailability;
        this.passengerAmount = passengerAmount;
        this.carSize = carSize;
        this.departureTime = departureTime;
    }

    public RouteDto(Route route) {
        this.startLocation = route.getStartLocation();
        this.endLocation = route.getEndLocation();
        this.driverId = route.getDriverId();
        this.routeLength = route.getRouteLength();
        this.timeInMinutes = route.getTimeInMinutes();
        this.handicapAvailability = route.isHandicapAvailability();
        this.passengerAmount = route.getPassengerAmount();
        this.carSize = route.getCarSize();
        this.departureTime = route.getDepartureTime();

    }

    public static List<RouteDto> toDTOList(List<Route> routeList) {
        return routeList.stream().map(RouteDto::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteDto routeDto)) return false;
        return driverId == routeDto.driverId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId);
    }
}
