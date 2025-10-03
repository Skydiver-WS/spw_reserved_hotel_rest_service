package ru.project.reserved.system.hotel.rest.service.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRs {

    private String commentId;
    private String message;
    private ErrorComment error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorComment{
        private String message;
    }
}
