package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.reserved.system.hotel.rest.service.dto.type.ClassRoomType;


import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRs {

    private UUID bookingId;
    private Date startReserved;
    private Date endReserved;
    private Double coast;
    private String numberApart;
    private String hotelId;
    private String roomId;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private ClassRoomType classRoomType;
    private String description;
    private String errorMessage;

}
