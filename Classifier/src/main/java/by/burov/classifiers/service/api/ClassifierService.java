package by.burov.classifiers.service.api;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ClassifierService<E,C,R> {

    E save(C dto);

    Page<R> readAll(int pageNo, int pageSize);

    R getByUuid(UUID uuid);




}
