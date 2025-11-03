package com.manuela.meucaixa.application.usecase;

public class DomainException extends IllegalStateException {
    public DomainException(String msg) {
        super(msg);
    }
}
