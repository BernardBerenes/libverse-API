package com.libverse.service;

import com.libverse.dto.request.CountryRequest;
import com.libverse.dto.response.CountryResponse;
import com.libverse.entity.Country;
import com.libverse.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    private CountryResponse toResponse(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .code(country.getCode())
                .name(country.getName())
                .build();
    }

    public List<CountryResponse> list() {
        return countryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void create(CountryRequest request) {
        if (countryRepository.existsByCode(request.getCode())) throw new IllegalArgumentException("Category name already exists");

        Country country = new Country();
        country.setCode(request.getCode());
        country.setName(request.getName());
        countryRepository.save(country);
    }

    public void update(Long countryId, CountryRequest request) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        if (countryRepository.existsByCodeAndIdNot(request.getName(), countryId)) throw new IllegalArgumentException("Country code already exists");

        country.setCode(request.getCode());
        country.setName(request.getName());
        countryRepository.save(country);
    }

    public void delete(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        country.setDeletedAt(Instant.now());
        countryRepository.save(country);
    }
}
