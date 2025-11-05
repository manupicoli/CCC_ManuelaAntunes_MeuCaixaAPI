package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.category.AddEditCategoryRequest;
import com.manuela.meucaixa.application.usecase.category.GetCategoryDetailsResponse;
import com.manuela.meucaixa.application.usecase.category.ListCategoryResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/v1/category")
public interface CategoryControllerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody AddEditCategoryRequest request);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long id,
                @Valid @RequestBody AddEditCategoryRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping("/{id}")
    GetCategoryDetailsResponse get(@PathVariable Long id);

    @GetMapping
    Page<ListCategoryResponse> list(@RequestParam(defaultValue = StringUtils.EMPTY) String qs,
                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page);
}
