package io.twodigits.urlshortener.controller;


import io.twodigits.urlshortener.service.URLShortenerServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlController {


    private final URLShortenerServiceImpl urlShortenerService;


    public UrlController(URLShortenerServiceImpl urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

//
//
//    @PostMapping("/new")
//    public void addURL (@RequestBody)


}
