package ru.project.reserved.system.hotel.rest.service.web.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityResponse {

    private Long id;

    private String name;

    private String address;
}
