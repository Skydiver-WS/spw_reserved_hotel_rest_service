package ru.project.reserved.system.hotel.rest.service.web.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityResponse {

    private Long id;

    private String name;
}
