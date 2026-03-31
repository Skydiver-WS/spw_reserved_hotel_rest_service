package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Photo implements Serializable {
    private UUID id;

    private String photo;
}
