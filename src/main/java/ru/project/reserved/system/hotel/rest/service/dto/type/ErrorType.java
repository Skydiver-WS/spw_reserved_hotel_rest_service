package ru.project.reserved.system.hotel.rest.service.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_RESPONSE_FROM_DB("Not response from server db");

    private final String errorMessage;
}
