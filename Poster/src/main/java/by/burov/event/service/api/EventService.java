package by.burov.event.service.api;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface EventService<T,C,R>{

    T save(C dto);

    Page<R> readAll(int pageNo, int pageSize);

    R getEventByUuid(UUID uuid);

    T update (UUID uuid, LocalDateTime dtUpdate, C dto);
}
