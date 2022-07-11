package by.burov.event.service.api;


import by.burov.event.core.dto.CreateConcertDto;
import by.burov.event.core.dto.ReadConcertDto;
import by.burov.event.core.dto.ReadEventDto;
import by.burov.event.repository.entity.Concert;

public interface ConcertService extends EventService<Concert, CreateConcertDto, ReadConcertDto> {

}
