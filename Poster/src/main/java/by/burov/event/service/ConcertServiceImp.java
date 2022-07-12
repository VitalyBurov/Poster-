package by.burov.event.service;

import by.burov.event.core.dto.CreateConcertDto;
import by.burov.event.core.dto.ReadConcertDto;
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

import java.time.LocalDateTime;
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
    public ReadConcertDto save(CreateConcertDto dto) {
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
            ReadConcertDto readConcertDto = mapperService.readConcertDto(concertsDao.save(concert));
            return readConcertDto;
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
    public ReadConcertDto update(UUID uuid, LocalDateTime dtUpdate, CreateConcertDto dto) {
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
            ReadConcertDto readConcertDto = mapperService.readConcertDto(concertsDao.save(concert));
            return readConcertDto;
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

}



