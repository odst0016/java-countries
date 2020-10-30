package com.odst0016.javacountries.repositories;

import com.odst0016.javacountries.models.Countries;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Countries, Long> {
}
