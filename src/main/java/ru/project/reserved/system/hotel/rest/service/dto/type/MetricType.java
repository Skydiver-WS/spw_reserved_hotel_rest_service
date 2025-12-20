package ru.project.reserved.system.hotel.rest.service.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetricType {

    CREATE_USER("create_user"),
    UPDATE_USER("update_user"),
    DELETE_USER("delete_user"),
    ALL_USERS("all_users"),
    CREATE_HOTEL("create_hotel"),
    UPDATE_HOTEL("update_hotel"),
    DELETE_HOTEL("delete_hotel"),
    ALL_HOTELS("all_hotels"),
    CREATE_ROOM("create_room"),
    UPDATE_ROOM("update_room"),
    DELETE_ROOM("delete_room"),
    ALL_ROOMS("all_rooms"),
    FIND_ROOMS("find_rooms"),
    CREATE_RESERVATION("create_reservation"),
    UPDATE_RESERVATION("update_reservation"),
    DELETE_RESERVATION("delete_reservation"),
    CREATE_COMMENT("create_comment"),
    UPDATE_COMMENT("update_comment"),
    DELETE_COMMENT("delete_comment"),
    SEARCH_HOTEL("search_hotel"),
    SIGN_IN_USER("sign_in_user"),
    NO_AUTH_USER("no_auth_user"),
    ACCESS_DENIED("access_denied"),
    ERROR("error");


    private final String type;
}
