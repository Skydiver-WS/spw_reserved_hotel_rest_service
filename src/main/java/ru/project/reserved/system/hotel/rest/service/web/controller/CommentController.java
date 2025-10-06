package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.CommentRq;
import ru.project.reserved.system.hotel.rest.service.web.response.CommentRs;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {


    private final ProxyService proxyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    public ResponseEntity<CommentRs> createComment(@RequestBody @Validated CommentRq commentRq){
        return ResponseEntity.ok((CommentRs) proxyService.proxyOperation(commentRq, CommentRs.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<CommentRs> updateComment(@RequestBody @Validated CommentRq commentRq){
        return ResponseEntity.ok((CommentRs) proxyService.proxyOperation(commentRq, CommentRs.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<CommentRs> deleteComment(@RequestBody @Validated CommentRq commentRq){
        return ResponseEntity.ok((CommentRs) proxyService.proxyOperation(commentRq, CommentRs.class));
    }

}
