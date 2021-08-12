package io.twodigits.urlshortener.repository;

import io.twodigits.urlshortener.model.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, String> {


    Iterable<Statistic> findByUrl(String url);

    Integer countAllByUrl(String url);


}
