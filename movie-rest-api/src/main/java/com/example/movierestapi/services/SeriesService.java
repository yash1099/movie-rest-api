package com.example.restapimongodb.services;



import com.example.restapimongodb.models.Series;
import com.example.restapimongodb.models.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService 
{
    @Autowired
    private SeriesRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Series> getSeries()

    {
        //validation or calculation or call model
        return repository.findAll();
    }

    public void insertIntoSeries(Series series)
    {
        repository.insert(series);
    }

    //it might return null so we use optional
    public Optional<Series> getASeries(String id) throws Exception
    {
        Optional<Series> series=repository.findById(id);
        if(!series.isPresent())
        {
            throw new Exception("Series with " + id + "is not found");
        }
        return series;
    }

    public void deleteASeries(String id)
    {
        repository.deleteById(id);
    }

    public List<Series> getSeriesWithTitle(String r) {
        // business logics
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(r));
        List<Series> series = mongoTemplate.find(query, Series.class);
        return series;
    }

    public Series editSeries(String id, Series newSeriesData)
    {
        // get the resource based on the id

        Optional<Series> series = repository.findById(id);

        // validation code to validate the id

        series.get().setTitle(newSeriesData.getTitle());
        series.get().setYear(newSeriesData.getYear());
        series.get().setBuy(newSeriesData.getBuy());
        series.get().setPoster(newSeriesData.getPoster());
        series.get().setOverview(newSeriesData.getOverview());
        series.get().setBackdrop_path(newSeriesData.getBackdrop_path());

        Series updateSeries = repository.save(series.get());

        return updateSeries;
    }
    
    
}
