package ru.project.reserved.system.hotel.rest.service.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameters {
    private Double rating;
    private Double distance;
    private Double coast;
}
