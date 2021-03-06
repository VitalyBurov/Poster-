package by.burov.event.service;

import by.burov.event.core.api.FieldValidationError;
import by.burov.event.core.dto.CreateConcertDto;
import by.burov.event.core.dto.CreateFilmDto;
import by.burov.event.core.dto.ReadFilmDto;
import by.burov.event.core.exception.FieldValidationException;
import by.burov.event.repository.FilmRepository;
import by.burov.event.repository.entity.Film;
import by.burov.event.service.api.FilmService;
import by.burov.event.service.api.MapperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class FilmServiceImp implements FilmService {

    private FilmRepository filmsDao;
    private MapperService mapperService;

    public FilmServiceImp(FilmRepository filmsDao, MapperService mapperService) {
        this.filmsDao = filmsDao;
        this.mapperService = mapperService;
    }

    @Override
    public Film save(CreateFilmDto dto) {
        isValid(dto);

        RestTemplate restTemplate = new RestTemplate();
        String concertResourceUrl = "http://localhost:80/api/v1/classifier/country";
        ResponseEntity<String> response = restTemplate.getForEntity(concertResourceUrl + "/" + dto.getCountry(), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Film film = mapperService.filmEntity(dto);
            film.setDtCreate(LocalDateTime.now());
            film.setDtUpdate(LocalDateTime.now());
            return filmsDao.save(film);
        } else {
            throw new IllegalArgumentException("No such country in country classifier!");
        }

    }

    @Override
    public Page<ReadFilmDto> readAll(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Film> filmsFromDB = filmsDao.findAll(paging);

        Page<ReadFilmDto> pagedResult = filmsFromDB.map(entity -> {
            ReadFilmDto dto = mapperService.readFilmDto(entity);
            return dto;
        });


        return pagedResult;
    }

    @Override
    public ReadFilmDto getEventByUuid(UUID uuid) {
        Film film = filmsDao.findById(uuid).get();
        ReadFilmDto dto = mapperService.readFilmDto(film);
        return dto;
    }

    @Override
    public Film update(UUID uuid, LocalDateTime dtUpdate, CreateFilmDto dto) {

        if (uuid == null) {
            throw new IllegalArgumentException("The field cannot be null");
        }

        isValid(dto);

        RestTemplate restTemplate = new RestTemplate();
        String concertResourceUrl = "http://localhost:80/api/v1/classifier/country";
        ResponseEntity<String> response = restTemplate.getForEntity(concertResourceUrl + "/" + dto.getCountry(), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ReadFilmDto dtoFromDB = this.getEventByUuid(uuid);
            if (!dtoFromDB.getDtUpdate().equals(dtUpdate)) {
                throw new IllegalArgumentException("The Film was updated before you!!!");
            }
            //should refactor check to null every field
            Film film = mapperService.filmEntity(dtoFromDB);
            addFields(dto, film);
            return filmsDao.save(film);
        } else {
            throw new IllegalArgumentException("No such country in country classifier!");
        }
    }

    private void addFields(CreateFilmDto dto, Film film) {
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setStatus(dto.getStatus());
        film.setType(dto.getType());
        film.setDtEvent(dto.getDtEvent());
        film.setDtEndOfSale(dto.getDtEndOfSale());
        film.setCountry(dto.getCountry());
        film.setReleaseDate(dto.getReleaseDate());
        film.setReleaseYear(dto.getReleaseYear());
        film.setDuration(dto.getDuration());
    }

    private boolean isValid(CreateFilmDto dto) {
        ArrayList<FieldValidationError> errors = new ArrayList<>();

        if (dto.getTitle() == null) {
            errors.add(new FieldValidationError("logref",

                    "The field \"title\" cannot be null"));
        }

        if (dto.getDescription() == null) {
            errors.add(new FieldValidationError("logref", "The field \"description\" cannot be null"));
        }
        if (dto.getStatus() == null) {
            errors.add(new FieldValidationError("logref", "The field \"status\" cannot be null"));
        }

        if (dto.getType() == null) {
            errors.add(new FieldValidationError("logref", "The field \"type\" cannot be null"));
        }

        if (dto.getDtEndOfSale() == null) {
            errors.add(new FieldValidationError("logref", "The field \"dt_end_of_sale\" cannot be null"));
        }
        if (dto.getDtEvent() == null) {
            errors.add(new FieldValidationError("logref", "The field \"dt_event\" cannot be null"));
        }
        if (dto.getCountry() == null) {
            errors.add(new FieldValidationError("logref", "The field \"country\" cannot be null"));
        }

        if (dto.getDuration() < 10) {
            errors.add(new FieldValidationError("logref", "The field \"duration\" cannot be null"));
        }

        if (dto.getReleaseYear() < 1800) {
            errors.add(new FieldValidationError("logref", "The field \"category\" cannot be null"));
        }

        if (dto.getReleaseDate() == null) {
            errors.add(new FieldValidationError("logref", "The field \"category\" cannot be null"));
        }


        if (!errors.isEmpty()) {
            throw new FieldValidationException("IllegalArgentException!!!", errors);
        } else {
            return true;
        }

    }
}
