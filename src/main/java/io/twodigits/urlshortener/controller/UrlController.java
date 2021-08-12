package io.twodigits.urlshortener.controller;


import io.twodigits.urlshortener.exception.NotFoundException;
import io.twodigits.urlshortener.model.URL;
import io.twodigits.urlshortener.service.StatisticServiceImpl;
import io.twodigits.urlshortener.service.URLShortenerServiceImpl;
import io.twodigits.urlshortener.validation.dto.URLDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class UrlController {

    @Autowired
    private URLShortenerServiceImpl urlShortenerService;

    @Autowired
    private StatisticServiceImpl statisticService;



    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<URLDto> createURL(@RequestBody @Valid URLDto urlDto) {

        URL createdURL = urlShortenerService.addURL(urlDto.getUser(), urlDto.getUrl());

        return new ResponseEntity<>(new URLDto(createdURL.getId(), createdURL.getURL(), createdURL.getUser()), HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void getURLById(@PathVariable String id, HttpServletResponse response, @RequestHeader Map<String, String> headers) {

        Optional<URL> urlDatabase = urlShortenerService.getURL(id);

        if (urlDatabase.isEmpty())
            throw new NotFoundException("URL with given ID doesn't exist");

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        try {
            response.sendRedirect(urlDatabase.get().getURL());
            statisticService.addStatistic(id, new Timestamp(System.currentTimeMillis()), headers.get("user-agent"), String.format("http://%s/%s", headers.get("host"), id), headers.get("cookie"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getURLByUserAndId(@RequestParam String user, @RequestParam String id, HttpServletResponse response, @RequestHeader Map<String, String> headers) {

        Optional<URL> urlDatabase = urlShortenerService.getURL(user, id);

        if (urlDatabase.isEmpty())
            throw new NotFoundException("Url with given ID and User doesn't exist");

        try {
            response.sendRedirect(urlDatabase.get().getURL());
            statisticService.addStatistic(id, new Timestamp(System.currentTimeMillis()), headers.get("user-agent"), String.format("http://%s/%s", headers.get("host"), id), headers.get("cookie"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @GetMapping(value = "/list/{user}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<List<URLDto>> getURLsForUser(@PathVariable String user) {

        List<URLDto> response = StreamSupport.stream(urlShortenerService.listURLs(user).spliterator(), false)
                .map(url -> new URLDto(url.getId(), url.getURL(), url.getUser()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateURL(@RequestBody @Valid URLDto urlDto) {
        urlShortenerService.updateURL(urlDto.getUser(), urlDto.getUrl(), urlDto.getId());
    }



    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteURL(@RequestParam String user, @RequestParam String id) {
        urlShortenerService.deleteURL(user, id);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return errors;
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());

    }


}
