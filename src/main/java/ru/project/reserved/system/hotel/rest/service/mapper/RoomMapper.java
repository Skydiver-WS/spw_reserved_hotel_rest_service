package ru.project.reserved.system.hotel.rest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRq;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {
    void mapperRoomRq(RoomRq rq, @MappingTarget RoomRq mappingTarget);
}
