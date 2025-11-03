package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.infrastructure.UseCase;
import org.springframework.data.domain.Page;

public interface ListFinancialRecordUseCase extends UseCase<ListFinancialRecordRequest, Page<ListFinancialRecordResponse>> {
}
