package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Photo {
    private UUID id;

    private String photo;
}
