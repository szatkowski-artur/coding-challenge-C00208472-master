package io.twodigits.urlshortener.repository;

import io.twodigits.urlshortener.model.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends CrudRepository<URL, String> {


    Iterable<URL> findByUser(String user);


    Optional<URL> findByIdAndUser(String id, String user);


}
