package ru.project.reserved.system.hotel.rest.service.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.project.reserved.system.db.app.service.entity.Photo;

import java.util.List;
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {
    private Long numberApart;

    private String description;

    private Status status;

    private ClassRoom classRoom;

    private List<Photo> photoList;

    private String errorMessage;
}