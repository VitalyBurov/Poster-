package by.burov.event.service.api;

import by.burov.event.core.dto.*;
import by.burov.event.repository.entity.Concert;
import by.burov.event.repository.entity.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperService {


    Film filmEntity(CreateFilmDto createDto);

    Concert concertEntity(CreateConcertDto createDto);

    ReadFilmDto readFilmDto(Film filmEntity);

    ReadConcertDto readConcertDto(Concert concertEntity);

    ReadConcertDto readConcertDto(CreateConcertDto createConcertDto);

    ReadFilmDto readFilmDto(CreateFilmDto createFilmDto);

    Film filmEntity(ReadFilmDto readFilmDto);

    Concert concertEntity(ReadConcertDto readConcertDto);


}