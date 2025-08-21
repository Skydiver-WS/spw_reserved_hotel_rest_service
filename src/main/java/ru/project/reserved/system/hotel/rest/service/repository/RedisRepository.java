package ru.project.reserved.system.hotel.rest.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;

import java.util.UUID;

@Repository
public interface RedisRepository extends CrudRepository<UUID, Redis> {


}
