package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.project.reserved.system.hotel.rest.service.dto.type.ClassRoomType;
import ru.project.reserved.system.hotel.rest.service.dto.Photo;
import ru.project.reserved.system.hotel.rest.service.dto.type.StatusType;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {
    private Long id;

    private UUID bookingId;

    private Long numberApart;

    private String description;

    private StatusType statusType;

    private ClassRoomType classRoomType;

    private List<Photo> photoList;

    private String errorMessage;
}
