package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.project.reserved.system.hotel.rest.service.dto.Photo;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponse {
    private Long id;
    private String name;
    private String description;
    private String city;
    private String address;
    private Double distance;
    private Double minCoast;
    private String rating;
    private Integer freeApart;
    private Integer countApart;
    private List<Photo> photos;
    private String errorMessage;
    private String message;
    private List<HotelResponse> hotels;
}