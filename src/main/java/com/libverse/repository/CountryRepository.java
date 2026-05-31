package com.libverse.repository;

import com.libverse.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long countryId);
}
