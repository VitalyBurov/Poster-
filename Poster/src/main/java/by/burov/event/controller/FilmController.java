package by.burov.event.controller;

import by.burov.event.core.api.APIConverter;
import by.burov.event.core.api.APIResponse;
import by.burov.event.core.dto.CreateFilmDto;
import by.burov.event.core.dto.ReadConcertDto;
import by.burov.event.core.dto.ReadFilmDto;
import by.burov.event.repository.entity.Film;
import by.burov.event.service.api.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/afisha/event/films")
public class FilmController {

    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    // change to DTO
    public ResponseEntity<Film> createFilm(@RequestBody CreateFilmDto film) {
        return new ResponseEntity<>(filmService.save(film), HttpStatus.CREATED);

    }

    // add pageable and responseEntity;
    @GetMapping
    public ResponseEntity<APIResponse<ReadFilmDto>> getAllFilms(@RequestParam(defaultValue = "1") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        APIResponse<ReadFilmDto> response = new APIConverter<ReadFilmDto>().convert(filmService.readAll(pageNo-1, pageSize));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ReadFilmDto> getEventByUuid(@PathVariable UUID uuid) {
        return new ResponseEntity<>(filmService.getEventByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    // change to DTO
    public ResponseEntity<Film> updateFilm(@PathVariable("uuid") UUID uuid,
                                           @PathVariable("dt_update") Long dt,
                                           @RequestBody CreateFilmDto film) {
        //Should refactor
        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt), ZoneId.systemDefault());

        return new ResponseEntity<>(filmService.update(uuid, dtUpdate, film), HttpStatus.CREATED);
    }


}
