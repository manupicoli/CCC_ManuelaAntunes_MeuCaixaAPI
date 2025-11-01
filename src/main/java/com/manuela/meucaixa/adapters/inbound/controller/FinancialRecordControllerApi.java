package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.financialrecord.AddEditFinancialRecordRequest;
import com.manuela.meucaixa.application.usecase.financialrecord.GetFinancialRecordDetailsResponse;
import com.manuela.meucaixa.application.usecase.financialrecord.ListFinancialRecordResponse;
import jakarta.validation.Valid;
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
    ListFinancialRecordResponse list();

    @GetMapping("/{id}")
    GetFinancialRecordDetailsResponse getDetails(@PathVariable Long id);
}
