package by.burov.classifiers.service.api;

import by.burov.classifiers.core.dto.CreateCountryDto;
import by.burov.classifiers.core.dto.ReadCountryDto;
import by.burov.classifiers.repository.entity.Country;

public interface CountryService extends ClassifierService<Country, CreateCountryDto, ReadCountryDto>{

}
