package ru.project.reserved.system.hotel.rest.service.dto.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeanType {

    MAIN_REST("main-rest"),
    GET_TOKEN("get-token"),
    PROMT("promt");

    private final String value;

}
