package ru.project.reserved.system.hotel.rest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
uses = {HotelMapper.class, RoomMapper.class})
public interface GigaChatMapper {

    @Mapping(target = "gigaChatRsInCache.token", source = "token")
    @Mapping(target = "gigaChatRsInCache.message", source = "message")
    @Mapping(target = "gigaChatRsInCache.content", source = "content")
    @Mapping(target = "gigaChatRsInCache.result", source = "result")
    @Mapping(target = "gigaChatRsInCache.hotelRq", source = "hotelRq")
    @Mapping(target = "gigaChatRsInCache.hotelRs", source = "hotelRs")
    @Mapping(target = "gigaChatRsInCache.roomRq", source = "roomRq")
    void gigaPromtRqFromGigaChatRs(GigaChatRs gigaChatRs, @MappingTarget PromtRq promtRq);

    void mappingGigaChatRs(GigaChatRs rs, @MappingTarget GigaChatRs targetRs);


}
