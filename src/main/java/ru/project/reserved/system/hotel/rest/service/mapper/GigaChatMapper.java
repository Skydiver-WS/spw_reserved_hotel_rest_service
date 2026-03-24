package ru.project.reserved.system.hotel.rest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GigaChatMapper {

    @Mapping(target = "gigaChatRsInCache.token", source = "token")
    @Mapping(target = "gigaChatRsInCache.message", source = "message")
    @Mapping(target = "gigaChatRsInCache.content", source = "content")
    @Mapping(target = "gigaChatRsInCache.result", source = "result")
    @Mapping(target = "gigaChatRsInCache.hotelRq", source = "hotelRq")
    @Mapping(target = "gigaChatRsInCache.hotelRs", source = "hotelRs")
    @Mapping(target = "gigaChatRsInCache.roomRq", source = "roomRq")
    void gigaPromtRqFromGigaChatRs(GigaChatRs gigaChatRs, @MappingTarget PromtRq promtRq);

//    @Mapping(source = "gigaChatRsInCache.token", target = "token")
//    @Mapping(source = "gigaChatRsInCache.message", target = "message")
//    @Mapping(source = "gigaChatRsInCache.content", target = "content")
//    @Mapping(source = "gigaChatRsInCache.result", target = "result")
//    @Mapping(source = "gigaChatRsInCache.hotelRq", target = "hotelRq")
//    @Mapping(source = "gigaChatRsInCache.hotelRs", target = "hotelRs")
//    @Mapping(source = "gigaChatRsInCache.roomRq", target = "roomRq")
//    void gigaGigaChatRsFromPromtRq(@MappingTarget GigaChatRs gigaChatRs, PromtRq promtRq);
}
