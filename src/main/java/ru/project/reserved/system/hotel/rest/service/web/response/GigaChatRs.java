package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRq;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRq;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class  GigaChatRs {

    @JsonProperty(value = "access_token")
    private String token;

    private String message;
    private String content;

    private HotelRq hotelRq;
    private RoomRq roomRq;
}
