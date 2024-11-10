package ru.project.reserved.system.hotel.rest.service.web.request;

import lombok.*;
import ru.project.reserved.system.hotel.rest.service.dto.City;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    private Long id;
    private String name;
    private String description;
    private String address;
    private Double distance;
    private Double rating;
    private List<City> cityList;
}
