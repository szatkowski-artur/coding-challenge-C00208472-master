package io.twodigits.urlshortener.service;

import io.twodigits.urlshortener.exception.NotFoundException;
import io.twodigits.urlshortener.model.Statistic;
import io.twodigits.urlshortener.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;



    @Override
    public Statistic addStatistic(String url, Timestamp timestamp, String userAgent, String refer, String cookies) {

        Statistic statistic = new Statistic();

        statistic.setUrl(url);
        statistic.setTimestamp(timestamp);
        statistic.setUserAgent(userAgent);
        statistic.setRefer(refer);
        statistic.setCookies(cookies);

        return statisticRepository.save(statistic);

    }



    @Override
    public Integer getNumberOfCallForURL(String url) {
        return statisticRepository.countAllByUrl(url);
    }



    @Override
    public Iterable<Statistic> listStatistics(String url) {
        return statisticRepository.findByUrl(url);
    }



    @Override
    public Optional<Statistic> getStatistic(String id) {
        return statisticRepository.findById(id);
    }



    @Override
    public void deleteStatistic(String id) throws NotFoundException {

        statisticRepository.delete(
                statisticRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Cannot find record")));

    }

}
