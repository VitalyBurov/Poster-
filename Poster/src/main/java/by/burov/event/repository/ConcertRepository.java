package by.burov.event.repository;

import by.burov.event.repository.entity.Concert;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConcertRepository extends JpaRepository<Concert,UUID> {

    @Override
    <S extends Concert> S save(S entity);

    @Override
    Page<Concert> findAll(Pageable pageable);

    @Override
    Optional<Concert> findById(UUID uuid);




}
