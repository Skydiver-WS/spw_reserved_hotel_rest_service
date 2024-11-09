package ru.project.reserved.system.hotel.rest.service.web.request;

import lombok.*;
import ru.project.reserved.system.db.app.service.entity.Photo;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    private Integer id;

    private Long numberApart;

    private String description;

    private Status status;

    private ClassRoom classRoom;

    private List<Photo> photoList;
}
