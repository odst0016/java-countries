package com.odst0016.javacountries.controllers;

import com.odst0016.javacountries.models.Countries;
import com.odst0016.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesController {
    @Autowired
    CountryRepository countryRepository;

    private List<Countries> filterCountries(List<Countries> myList, CheckCountries tester) {
        List<Countries> testList = new ArrayList<>();

        for (Countries c : myList) {
            if (tester.test(c)) {
                testList.add(c);
            }
        }
        return testList;
    }

    //shows all countries
    @GetMapping(value = "/countries/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries() {
        List<Countries> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //searches country name by letter
    @GetMapping(value = "/countries/name/{letter}", produces = "application/json")
    public ResponseEntity<?> findCountryByFirstLetter(@PathVariable char letter) {
        List<Countries> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        List<Countries> rtnList = filterCountries(myList, c -> c.getName().charAt(0) == letter);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

//    <summary>http://localhost:2019/population/total</summary>
    @GetMapping(value = "/countries/population/total", produces = "application/json")
    public ResponseEntity<?> findTotalPopulation() {
        List<Countries> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;

        for (Countries c : myList) {
            total += c.getPopulation();
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

//    <summary>http://localhost:2019/population/min</summary>

    @GetMapping(value = "/countries/population/min", produces = "application/json")
    public ResponseEntity<?> findMinPolulation() {
        List<Countries> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) -> c1.getPopulation() > c2.getPopulation() ? 1: -1);
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);


    }

    @GetMapping(value = "/countries/population/max", produces = "application/json")
    public ResponseEntity<?> findMaxPolulation() {
        List<Countries> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) -> c1.getPopulation() < c2.getPopulation() ? 1: -1);
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);


    }
}
