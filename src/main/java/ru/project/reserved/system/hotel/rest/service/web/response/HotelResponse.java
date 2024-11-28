package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Double distance;
    private String rating;
    private Integer freeApart;
    private Integer countApart;
    private String errorMessage;
    private String message;
    private List<CityResponse> cityList;
    private List<RoomResponse> roomList;
}
