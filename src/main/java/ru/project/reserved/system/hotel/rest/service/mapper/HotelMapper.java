package ru.project.reserved.system.hotel.rest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRq;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HotelMapper {

    void mappingHotelRq(HotelRq rq, @MappingTarget HotelRq targetHotel);
    void mappingHotelSearch(HotelRq.HotelSearchRequest rq, @MappingTarget HotelRq.HotelSearchRequest targetSearch);
}
