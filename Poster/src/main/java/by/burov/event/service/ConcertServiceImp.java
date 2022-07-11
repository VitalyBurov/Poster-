package by.burov.event.service;

import by.burov.event.core.api.FieldValidationError;
import by.burov.event.core.dto.CreateConcertDto;
import by.burov.event.core.dto.ReadConcertDto;
import by.burov.event.core.exception.FieldValidationException;
import by.burov.event.repository.ConcertRepository;
import by.burov.event.repository.entity.Concert;
import by.burov.event.service.api.ConcertService;
import by.burov.event.service.api.MapperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ConcertServiceImp implements ConcertService {

    private ConcertRepository concertsDao;
    private MapperService mapperService;

    public ConcertServiceImp(ConcertRepository concertsDao, MapperService mapperService) {
        this.concertsDao = concertsDao;
        this.mapperService = mapperService;
    }

    @Override
    public Concert save(CreateConcertDto dto) {

        isValid(dto);

        RestTemplate restTemplate = new RestTemplate();
        String concertResourceUrl
                //add to properties
                = "http://localhost:80/api/v1/classifier/concert/category";
        ResponseEntity<String> response
                = restTemplate.getForEntity(concertResourceUrl + "/" + dto.getCategory(),
                String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Concert concert = mapperService.concertEntity(dto);
            concert.setDtCreate(LocalDateTime.now());
            concert.setDtUpdate(LocalDateTime.now());
            return concertsDao.save(concert);
        } else {
            throw new IllegalArgumentException("No such category in concert category classifier!");
        }
    }


    @Override
    public Page<ReadConcertDto> readAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Concert> concertFromDB = concertsDao.findAll(paging);

        Page<ReadConcertDto> pagedResult = concertFromDB.map(entity -> {
            ReadConcertDto dto = mapperService.readConcertDto(entity);
            return dto;
        });
        return pagedResult;
    }

    @Override
    public ReadConcertDto getEventByUuid(UUID uuid) {
        Concert concert = concertsDao.findById(uuid).get();
        ReadConcertDto dto = mapperService.readConcertDto(concert);
        return dto;
    }


    @Override
    public Concert update(UUID uuid, LocalDateTime dtUpdate, CreateConcertDto dto) {
        isValid(dto);

        if (uuid == null) {
            throw new IllegalArgumentException("The field cannot be null");
        }

        RestTemplate restTemplate = new RestTemplate();
        String concertResourceUrl
                = "http://localhost:80/api/v1/classifier/concert/category";
        ResponseEntity<String> response
                = restTemplate.getForEntity(concertResourceUrl + "/" + dto.getCategory(),
                String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ReadConcertDto dtoFromDB = this.getEventByUuid(uuid);
            if (!dtoFromDB.getDtUpdate().equals(dtUpdate)) {
                throw new IllegalArgumentException("The Film was updated before you!!!");
            }
            //should refactor check to null every field
            Concert concert = mapperService.concertEntity(dtoFromDB);
            // concert = mapperService.concertEntity(dto);
            addFields(dto, concert);
            return concertsDao.save(concert);
        } else {
            throw new IllegalArgumentException("No such category in concert category!");
        }
    }

    private void addFields(CreateConcertDto dto, Concert concert) {
        concert.setTitle(dto.getTitle());
        concert.setDescription(dto.getDescription());
        concert.setStatus(dto.getStatus());
        concert.setType(dto.getType());
        concert.setDtEvent(dto.getDtEvent());
        concert.setDtEndOfSale(dto.getDtEndOfSale());
        concert.setCategory(dto.getCategory());
    }

    private boolean isValid(CreateConcertDto dto) {
        ArrayList<FieldValidationError> errors = new ArrayList<>();
        if (dto.getTitle() == null) {
            errors.add(new FieldValidationError("logref","The field \"title\" cannot be null"));
        }
        if (dto.getDescription() == null) {
            errors.add(new FieldValidationError("logref","The field \"description\" cannot be null"));
        }
        if (dto.getStatus() == null) {
            errors.add(new FieldValidationError("logref","The field \"status\" cannot be null"));
        }
        if (dto.getType() == null) {
            errors.add(new FieldValidationError("logref",  "The field \"type\" cannot be null"));
        }
        if (dto.getCategory() == null) {
            errors.add(new FieldValidationError("logref","The field \"category\" cannot be null"));
        }
        if (dto.getDtEndOfSale() == null) {
            errors.add(new FieldValidationError("logref","The field \"dt_end_of_sale\" cannot be null"));
        }
        if (dto.getDtEvent() == null) {
            errors.add(new FieldValidationError("logref", "The field \"dt_event\" cannot be null"));
        }
        if (!errors.isEmpty()) {
            throw new FieldValidationException("IllegalArgentException!!!", errors);
        } else {
            return true;
        }

        /*for (Field field : dto.getClass().getFields()) {
            field.setAccessible(true);
            if (field== null) {
                errors.add(new FieldValidationError("logref",
                        "The field " + field.getName() + " cannot be null"));
            }

        }
        if (!errors.isEmpty()) {
            throw new FieldValidationException("IllegalArgentException!!!", errors);
        } else {
            return true;
        }
    }*/
    }

}


