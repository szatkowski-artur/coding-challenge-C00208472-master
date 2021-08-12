package io.twodigits.urlshortener.service;

import io.twodigits.urlshortener.model.Statistic;

import java.sql.Timestamp;
import java.util.Optional;

public interface StatisticService {


    Statistic addStatistic(String url, Timestamp timestamp, String userAgent, String refer, String cookies);

    Integer getNumberOfCallForURL(String url);

    Iterable<Statistic> listStatistics(String url);

    Optional<Statistic> getStatistic(String id);

    void deleteStatistic(String id);
}
