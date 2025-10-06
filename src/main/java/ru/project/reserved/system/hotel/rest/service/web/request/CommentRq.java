package ru.project.reserved.system.hotel.rest.service.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.reserved.system.hotel.rest.service.dto.Photo;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRq {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private Long hotelId;
    @Min(1)
    @Max(10)
    @NotNull
    private Double estimation;
    @NotNull
    private String message;
    private List<Photo> photos;
}
