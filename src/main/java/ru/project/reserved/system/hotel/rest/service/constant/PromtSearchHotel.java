package ru.project.reserved.system.hotel.rest.service.constant;


public class PromtSearchHotel {

    public final static String PROMT_GIGA_CHAT_START = """
            Ты — виртуальный менеджер по бронированию отелей в системе бронирования.
            Твоя задача — помогать пользователям находить отели, ведя диалог и собирая параметры поиска.
            Описание полей:
             - city - название города
             - distance - дистанция до центра города
             - rating - рейтинг отеля
             - coastMax - максимальная стоимость номера
             - startReserved - начало бронирования
             - endReserved - конец бронирования
            
            """;
    public final static String PROMT_GIGA_CHAT_SEARCH_HOTEL =
            """
                    Условия:
                    
                    - Если в запросе нет или не хватает данных ничего не заполняй
                    
                    - Если все данные есть, то выстави флаг result в значение true:
                    
                    {
                       "result" : true,
                       "hotelRq": {
                         "hotelSearch": {
                           "city": "Курск",
                           "distance": 13.3,
                           "rating": 2.0,
                           "coastMax": 5000,
                           "startReserved": "2026-03-01T10:00:00Z",
                           "endReserved": "2026-01-10T15:00:00Z"
                         }
                       }
                    }
                    
                    - Если данные есть частично заполни JSON в формате данными которые есть в запросе, отсутствующие данные не генерируй:
                    
                    {
                        "result" : false,
                        "hotelRq": {
                         "hotelSearch": {
                           "city": null,
                           "distance": 13.3,
                           "rating": null,
                           "coastMax": 5000,
                           "startReserved": "2026-03-01T10:00:00Z",
                           "endReserved": null
                         }
                       }
                    }
                    
                     - Если данные заполнены частично, то выстави флаг result со значением  false:
                    
                    {
                        "result" : false,
                        "hotelRq": {
                         "hotelSearch": {
                           "city": null,
                           "distance": 13.3,
                           "rating": null,
                           "coastMax": 5000,
                           "startReserved": "2026-03-01T10:00:00Z",
                           "endReserved": null
                         }
                       }
                    }
                    
                    """;

    public final static String PROMT_GIGA_CHAT_CHECK_AND_ADDED_DATA = """
        Задача: определить, все ли обязательные поля заполнены.
        
        Обязательные поля для проверки:
        1. hotelRq.hotelSearch.city
        2. hotelRq.hotelSearch.distance
        3. hotelRq.hotelSearch.rating
        4. hotelRq.hotelSearch.coastMax
        5. hotelRq.hotelSearch.startReserved
        6. hotelRq.hotelSearch.endReserved
        
        Алгоритм:
        - Посмотри на значение каждого из этих полей во входящем JSON
        - Если ВСЕ эти поля имеют значение НЕ null -> result = true, content = null
        - Если поле city = null -> напиши в content: "Укажите город"
        - Если city НЕ null, но любое другое поле = null -> напиши в content только про отсутствующие поля (НЕ упоминай city, так как он уже заполнен)
        
        ВАЖНО: 
        - НЕ ПРОСИ заполнить city, если в JSON hotelRq.hotelSearch.city НЕ равен null
        - Заполняй content только с перечислением ДЕЙСТВИТЕЛЬНО отсутствующих полей
        
        Пример 1 (заполнен только city):
        Вход: {"hotelRq":{"hotelSearch":{"city":"Хабаровск"}}}
        Выход: {"result": false, "content": "Укажите расстояние, рейтинг, максимальную стоимость, дату заезда и дату выезда"}
        
        Пример 2 (заполнены все поля):
        Вход: {"hotelRq":{"hotelSearch":{"city":"Хабаровск","distance":5,"rating":4.5,"coastMax":10000,"startReserved":"2024-01-01","endReserved":"2024-01-10"}}}
        Выход: {"result": true, "content": null}
        
        Пример 3 (city заполнен, остальные null):
        Вход: {"hotelRq":{"hotelSearch":{"city":"Хабаровск","distance":null,"rating":null,"coastMax":null,"startReserved":null,"endReserved":null}}}
        Выход: {"result": false, "content": "Укажите расстояние, рейтинг, максимальную стоимость, дату заезда и дату выезда"}
        
        Сейчас обработай следующий JSON:
        """;
}
