package ru.project.reserved.system.hotel.rest.service.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromtRq {
    private final String role = "user";
    private String content;
}
