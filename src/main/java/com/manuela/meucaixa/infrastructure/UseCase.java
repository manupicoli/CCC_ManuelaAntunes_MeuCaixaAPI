package com.manuela.meucaixa.infrastructure;

public interface UseCase<IN, OUT> {
    OUT execute(IN in);
}
