package by.burov.event.service.api;

import by.burov.event.core.dto.CreateFilmDto;
import by.burov.event.core.dto.ReadEventDto;
import by.burov.event.core.dto.ReadFilmDto;
import by.burov.event.repository.entity.Film;
;


public interface FilmService extends EventService<Film, CreateFilmDto, ReadFilmDto> {
}
