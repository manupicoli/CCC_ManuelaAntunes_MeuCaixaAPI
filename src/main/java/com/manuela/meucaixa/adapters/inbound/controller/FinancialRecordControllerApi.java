package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.financialrecord.AddEditFinancialRecordRequest;
import com.manuela.meucaixa.application.usecase.financialrecord.GetFinancialRecordDetailsResponse;
import com.manuela.meucaixa.application.usecase.financialrecord.ListFinancialRecordResponse;
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
@RequestMapping("/v1/financial-record")
public interface FinancialRecordControllerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody @Valid AddEditFinancialRecordRequest request);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long id,
                @RequestBody @Valid AddEditFinancialRecordRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping
    Page<ListFinancialRecordResponse> list(@RequestParam(defaultValue = StringUtils.EMPTY) String qs,
                                           @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page);

    @GetMapping("/{id}")
    GetFinancialRecordDetailsResponse getDetails(@PathVariable Long id);
}
