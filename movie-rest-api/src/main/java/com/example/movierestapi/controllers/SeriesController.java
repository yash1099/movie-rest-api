package com.example.movierestapi.controllers;

import com.example.restapimongodb.CustomizedResponse;
import com.example.restapimongodb.models.Series;
import com.example.restapimongodb.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class SeriesController 
{
    @Autowired
    private SeriesService service;


    @GetMapping("/series")
    public ResponseEntity getSeries()
    {
        var customizedResponse= new CustomizedResponse("List of series", service.getSeries());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/series/{id}")
    public ResponseEntity getASeries(@PathVariable("id")String id) {
        CustomizedResponse customizedResponse= null;
        try {
            customizedResponse = new CustomizedResponse("Series wih id "+ id , Collections.singletonList(service.getASeries(id)));
        } catch (Exception e) {

            customizedResponse=new CustomizedResponse(e.getMessage(), null);

            return new ResponseEntity(customizedResponse,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(customizedResponse,HttpStatus.OK);
    }

    @DeleteMapping("/series/{id}")
    public ResponseEntity deleteASeries(@PathVariable("id")String id)
    {
        service.deleteASeries(id);
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/series/title")
    public ResponseEntity getSeriesByTitle(@RequestParam(value = "title") String r)
    {

        CustomizedResponse customizedResponse = new CustomizedResponse(" A list of series with the title : " , service.getSeriesWithTitle(r));

        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/series", consumes = { //Consume is when we are sending the data into body of req
            MediaType.APPLICATION_JSON_VALUE
    })

    public ResponseEntity addSeries(@RequestBody Series series)
    {
        service.insertIntoSeries(series);
        return new ResponseEntity(series, HttpStatus.OK);

    }
    @PutMapping(value = "/series/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editSeries(@PathVariable("id") String id, @RequestBody Series newSeries )

    {
        var customizedResponse = new CustomizedResponse(" Series with ID:  " + id + "updated successfully " , Collections.singletonList(service.editSeries(id, newSeries)));

        return new ResponseEntity(customizedResponse, HttpStatus.OK);

    }
}
