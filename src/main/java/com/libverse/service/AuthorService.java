package com.libverse.service;

import com.libverse.dto.request.AuthorRequest;
import com.libverse.dto.response.AuthorResponse;
import com.libverse.dto.response.CountryResponse;
import com.libverse.entity.Author;
import com.libverse.entity.Country;
import com.libverse.repository.AuthorRepository;
import com.libverse.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    private final CountryRepository countryRepository;

    private AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .nationality(CountryResponse.builder()
                        .id(author.getNationality().getId())
                        .code(author.getNationality().getCode())
                        .name(author.getNationality().getName())
                        .build())
                .name(author.getName())
                .biography(author.getBiography())
                .birthDate(author.getBirthDate())
                .build();
    }

    public Page<AuthorResponse> paginate(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        return (name != null && !name.isBlank()) ? authorRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toResponse) : authorRepository.findAll(pageable).map(this::toResponse);
    }

    public void create(AuthorRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        Author author = new Author();
        author.setNationality(country);
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setBirthDate(request.getBirthDate());
        authorRepository.save(author);
    }
}
