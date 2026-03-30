package ru.project.reserved.system.hotel.rest.service.utils;

import org.apache.logging.log4j.util.Strings;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRq;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HotelRqValidateUtils {
    public static String[] validateHotelRq(HotelRq hotelRq) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(hotelRq)) {
            errors.add("hotelRq is null");
            return errors.toArray(new String[0]);
        }
        if (Objects.isNull(hotelRq.getHotelSearch())){
            errors.add("hotelSearch is null");
            return errors.toArray(new String[0]);
        }
        HotelRq.HotelSearchRequest hotelSearchRequest = hotelRq.getHotelSearch();
        if (Strings.isBlank(hotelSearchRequest.getCity())){
            errors.add("city");
        }
        if (Objects.isNull(hotelSearchRequest.getStartReserved())){
            errors.add("startReserved");
        }
        if (Objects.isNull(hotelSearchRequest.getEndReserved())){
            errors.add("endReserved");
        }
        if (Objects.isNull(hotelSearchRequest.getDistance())){
            errors.add("distance");
        }
        if(Objects.isNull(hotelSearchRequest.getRating())){
            errors.add("rating");
        }
        if(Objects.isNull(hotelSearchRequest.getCoastMax())){
            errors.add("coastMax");
        }
        return errors.toArray(new String[0]);
    }
}
