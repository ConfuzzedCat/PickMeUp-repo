package dk.lyngby.dto;

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
    private String departureLocation;
    private String destination;
    private LocalDateTime departureDateTime;

    public RouteDto(Route route) {
        this.id = route.getId();
        this.departureLocation = route.getStartLocation();
        this.destination = route.getEndLocation();
        this.departureDateTime = route.getDepartureDateTime();
    }

    public RouteDto(String departureLocation, String destination, LocalDateTime departureDateTime) {
        this.departureLocation = departureLocation;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
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
