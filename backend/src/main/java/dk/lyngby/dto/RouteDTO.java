package dk.lyngby.dto;

import dk.lyngby.model.Driver;
import dk.lyngby.model.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RouteDto {

    private Integer id;

    private String startLocation;
    private String endLocation;
    private LocalDateTime departureDateTime;
    private Integer startPostalCode;
    private Integer endPostalCode;
    private double routeLength;
    private int timeInMinutes;
    private boolean handicapAvailability;
    private int passengerAmount;
    private int carSize;

    public RouteDto(Route route) {
        this.id = route.getId();
        this.startPostalCode = route.getStartPostalCode();
        this.endPostalCode = route.getEndPostalCode();
        this.startLocation = route.getStartLocation();
        this.endLocation = route.getEndLocation();
        this.routeLength = route.getRouteLength();
        this.timeInMinutes = route.getTimeInMinutes();
        this.handicapAvailability = route.isHandicapAvailability();
        this.passengerAmount = route.getPassengerAmount();
        this.carSize = route.getCarSize();
        this.departureDateTime = route.getDepartureDateTime();
    }

    public static List<RouteDto> toRouteDTOList(List<Route> routes) {
        return routes.stream().map(RouteDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteDto routeDto)) return false;

        return getId().equals(routeDto.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
