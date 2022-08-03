package com.example.movierestapi.services;

import com.example.restapimongodb.models.Movie;
import com.example.restapimongodb.models.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService
{
    @Autowired
private MovieRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Movie> getMovies()

    {
       //validation or calculation or call model
        return repository.findAll();
    }

    public void insertIntoMovies(Movie movie)
    {
        repository.insert(movie);
    }

    //it might return null so we use optional
    public Optional<Movie> getAMovie(String id) throws Exception
    {
        Optional<Movie> movie=repository.findById(id);
         if(!movie.isPresent())
         {
             throw new Exception("Movie with " + id + "is not found");
         }
         return movie;
    }

    public void deleteAMovie(String id)
    {
        repository.deleteById(id);
    }
    public List<Movie> getMoviesWithTitle(String r) {
        // business logics
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(r));
        List<Movie> movies = mongoTemplate.find(query, Movie.class);
        return movies;
    }

    public Movie editMovie(String id, Movie newMovieData)
    {
        // get the resource based on the id

        Optional<Movie> movie = repository.findById(id);

        // validation code to validate the id

        movie.get().setTitle(newMovieData.getTitle());
        movie.get().setYear(newMovieData.getYear());
        movie.get().setRent(newMovieData.getRent());
        movie.get().setBuy(newMovieData.getBuy());
        movie.get().setPoster(newMovieData.getPoster());
        movie.get().setOverview(newMovieData.getOverview());
        movie.get().setBackdrop_path(newMovieData.getBackdrop_path());

        Movie updateMovie = repository.save(movie.get());

        return updateMovie;
    }
}
