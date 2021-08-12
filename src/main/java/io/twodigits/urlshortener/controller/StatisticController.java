package io.twodigits.urlshortener.controller;

import io.twodigits.urlshortener.exception.NotFoundException;
import io.twodigits.urlshortener.model.Statistic;
import io.twodigits.urlshortener.service.StatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticServiceImpl statisticService;



    @GetMapping("/count/{url}")
    public ResponseEntity<Integer> getCountForURL(@PathVariable String url) {

        return new ResponseEntity<>(statisticService.getNumberOfCallForURL(url), HttpStatus.OK);

    }



    @GetMapping("/url/{url}")
    public ResponseEntity<List<Statistic>> getStatisticForUrl(@PathVariable String url) {

        System.out.println(url);

        List<Statistic> statistics = StreamSupport.stream(statisticService.listStatistics(url).spliterator(), false)
                .collect(Collectors.toList());

        return new ResponseEntity<>(statistics, HttpStatus.OK);

    }



    @GetMapping("/{id}")
    public ResponseEntity<Statistic> getStatisticById(@PathVariable String id) {

        Optional<Statistic> statistic = statisticService.getStatistic(id);

        if (statistic.isEmpty())
            throw new NotFoundException("Statistic with given ID doesn't exist");

        return new ResponseEntity<>(statistic.get(), HttpStatus.OK);

    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatistic(@PathVariable String id) {
        statisticService.deleteStatistic(id);
    }

}
