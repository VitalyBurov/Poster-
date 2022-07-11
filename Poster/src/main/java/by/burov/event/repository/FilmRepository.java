package by.burov.event.repository;

import by.burov.event.repository.entity.Concert;
import by.burov.event.repository.entity.Film;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmRepository extends JpaRepository<Film,UUID> {

    @Override
    <S extends Film> S save(S entity);

    @Override
    <S extends Film> List<S> findAll(Example<S> example);

    @Override
    Optional<Film> findById(UUID uuid);

}