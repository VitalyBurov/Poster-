package by.burov.classifiers.service.api;

import by.burov.classifiers.core.dto.CreateConcertCategoryDto;
import by.burov.classifiers.core.dto.ReadConcertCategoryDto;
import by.burov.classifiers.repository.entity.ConcertCategory;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ConcertService extends ClassifierService<ConcertCategory,CreateConcertCategoryDto,ReadConcertCategoryDto>{




}
