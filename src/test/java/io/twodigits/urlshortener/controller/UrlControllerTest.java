package io.twodigits.urlshortener.controller;

import io.twodigits.urlshortener.model.URL;
import io.twodigits.urlshortener.service.StatisticServiceImpl;
import io.twodigits.urlshortener.service.URLShortenerServiceImpl;
import org.apache.tomcat.util.digester.ArrayStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLShortenerServiceImpl urlShortenerService;

    @MockBean
    private StatisticServiceImpl statisticService;



    @BeforeEach
    public void setUp() {

        URL urlMock = new URL();
        urlMock.setUrl("www.test.com");
        urlMock.setUser("userId");

        when(urlShortenerService.addURL("userId", "www.test.com")).thenReturn(urlMock);

        when(urlShortenerService.getURL(anyString())).thenReturn(Optional.of(urlMock));

        when(urlShortenerService.getURL("notFound")).thenReturn(Optional.empty());

        when(urlShortenerService.getURL("user", "id")).thenReturn(Optional.of(urlMock));

        when(urlShortenerService.listURLs("user")).thenReturn(Arrays.asList(urlMock, urlMock, urlMock));


    }



    @Test
    public void createURL_basic() {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/")
                .content("{\"url\":\"www.test.com\",\"user\":\"userId\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(content().json("{url:www.test.com,user:userId}"))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void createURL_badRequest() {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/")
                .content("{\"url\":\"test\",\"user\":\"userId\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getURLById_basic() {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/id")
                .accept(MediaType.APPLICATION_JSON);


        try {
            mockMvc.perform(request)
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("www.test.com"))
                    .andReturn();

            verify(urlShortenerService, times(1)).getURL("id");

            verify(statisticService, times(1)).addStatistic(anyString(), any(), nullable(String.class), anyString(), nullable(String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getURLById_notFound() {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/notFound")
                .accept(MediaType.APPLICATION_JSON);


        try {
            mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andReturn();

            verify(urlShortenerService, times(1)).getURL("notFound");

            verify(statisticService, times(0)).addStatistic(anyString(), any(), nullable(String.class), anyString(), nullable(String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getURLByUserAndId_basic() {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/")
                .queryParam("user", "user")
                .queryParam("id", "id");


        try {
            MvcResult result = mockMvc.perform(request)
                    .andExpect(status().is3xxRedirection())
                    .andReturn();

            verify(urlShortenerService, times(1)).getURL("user", "id");

            verify(statisticService, times(1)).addStatistic(anyString(), any(), nullable(String.class), anyString(), nullable(String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getURLByUserAndId_notFound() {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/")
                .queryParam("user", "notFound")
                .queryParam("id", "id");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andReturn();

            verify(urlShortenerService, times(1)).getURL("notFound", "id");

            verify(statisticService, times(0)).addStatistic(anyString(), any(), nullable(String.class), anyString(), nullable(String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getURLsForUser() {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/list/user")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{url:www.test.com,user:userId}, {url:www.test.com,user:userId}, {url:www.test.com,user:userId}]"))
                    .andReturn();

            verify(urlShortenerService, times(1)).listURLs("user");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void updateURL_basic() {

        RequestBuilder request = MockMvcRequestBuilders
                .put("/")
                .content("{\"url\":\"www.test.com\",\"user\":\"userId\", \"id\":\"update\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isNoContent())
                    .andReturn();

            verify(urlShortenerService, times(1)).updateURL("userId", "www.test.com", "update");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateURL_badRequest () {

        RequestBuilder request = MockMvcRequestBuilders
                .put("/")
                .content("{\"url\":\"test\",\"user\":\"userId\", \"id\":\"update\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andReturn();

            verify(urlShortenerService, times(0)).updateURL(anyString(), anyString(), anyString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void deleteURL() {

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/")
                .queryParam("user", "user")
                .queryParam("id", "id");


        try {
            mockMvc.perform(request)
                    .andExpect(status().isNoContent())
                    .andReturn();

            verify(urlShortenerService, times(1)).deleteURL(anyString(), anyString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}