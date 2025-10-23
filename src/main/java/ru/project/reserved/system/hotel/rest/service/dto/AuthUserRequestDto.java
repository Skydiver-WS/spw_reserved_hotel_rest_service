package ru.project.reserved.system.hotel.rest.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthUserRequestDto {
    private UUID userId;
    private String user;
    private String username;
    private String password;
    private Long hotelId;
    private List<Role> role;
    private AdminRequest admin;
    private ManagerRequest manager;
    private EmployeeRequest employee;
    private ClientRequest client;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AdminRequest {
        @NotNull
        private String firstName;
        @NotNull
        private String lastName;
        @NotNull
        private String middleName;
        @NotNull
        private String inn;
        @NotNull
        private String ogrn;
        @NotNull
        private String address;
        @NotNull
        private String phone;
        @NotNull
        @Email
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManagerRequest {
        @NotNull
        private String firstName;
        @NotNull
        private String lastName;
        @NotNull
        private String middleName;
        @NotNull
        private String inn;
        @NotNull
        private String ogrn;
        @NotNull
        private String address;
        @NotNull
        private String phone;
        @NotNull
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeRequest {
        @NotNull
        private String firstName;
        @NotNull
        private String lastName;
        @NotNull
        private String middleName;
        @NotNull
        private String phone;
        @NotNull
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientRequest {
        @NotNull
        private String firstName;
        @NotNull
        private String lastName;
        @NotNull
        private String middleName;
        @NotNull
        private String phone;
        @NotNull
        private String email;
    }
}
