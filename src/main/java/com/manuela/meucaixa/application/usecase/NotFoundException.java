package com.manuela.meucaixa.application.usecase;

public class NotFoundException extends DomainException {
    public NotFoundException() {
        super("Não foi possível encontrar o registro.");
    }
}
