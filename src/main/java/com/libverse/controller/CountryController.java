package com.libverse.controller;

import com.libverse.dto.request.CountryRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.CountryResponse;
import com.libverse.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> list() {
        List<CountryResponse> countryResponse = countryService.list();

        return ResponseEntity.ok(ApiResponse.success("Successfully get data", countryResponse));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CountryRequest request) {
        countryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }

    @PatchMapping("/{countryId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long countryId, @Valid @RequestBody CountryRequest request) {
        countryService.update(countryId, request);

        return ResponseEntity.ok(ApiResponse.success("Successfully update data"));
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long countryId) {
        countryService.delete(countryId);

        return ResponseEntity.ok(ApiResponse.success("Successfully delete data"));
    }
}
