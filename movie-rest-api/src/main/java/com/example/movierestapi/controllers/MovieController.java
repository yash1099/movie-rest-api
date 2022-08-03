package com.example.movierestapi.controllers;

import com.example.restapimongodb.CustomizedResponse;
import com.example.restapimongodb.models.Movie;
import com.example.restapimongodb.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

//@CrossOrigin(origins="http://localhost:3000")
@RestController
public class MovieController
{
    @Autowired
    private MovieService service;


    @GetMapping("/movies")
    public ResponseEntity getMovies()
    {//datatype is response entity caz we want to send a proper response. and showing the status code.
        var customizedResponse= new CustomizedResponse("List of movies", service.getMovies());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity getAMovie(@PathVariable("id")String id) {
        CustomizedResponse customizedResponse= null;
        try {
            customizedResponse = new CustomizedResponse("Movie wih id "+ id , Collections.singletonList(service.getAMovie(id)));
        } catch (Exception e) {

            customizedResponse=new CustomizedResponse(e.getMessage(), null);

            return new ResponseEntity(customizedResponse,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(customizedResponse,HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity deleteAMovie(@PathVariable("id")String id)
    {
        service.deleteAMovie(id);
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/movies/title")
    public ResponseEntity getMoviesByRating(@RequestParam(value = "title") String r)
    {

        CustomizedResponse customizedResponse = new CustomizedResponse(" A list of movies with the title : " , service.getMoviesWithTitle(r));

        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/movies", consumes = { //Consume is when we are sending the data into body of req
            MediaType.APPLICATION_JSON_VALUE
    })

    public ResponseEntity addMovie(@RequestBody Movie movie)
    {
        service.insertIntoMovies(movie);
        return new ResponseEntity(movie, HttpStatus.OK);

    }
    @PutMapping(value = "/movies/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editMovie(@PathVariable("id") String id, @RequestBody Movie newMovie )

    {
        var customizedResponse = new CustomizedResponse(" Movie with ID:  " + id + " updated successfully " , Collections.singletonList(service.editMovie(id, newMovie)));

        return new ResponseEntity(customizedResponse, HttpStatus.OK);

    }
}
