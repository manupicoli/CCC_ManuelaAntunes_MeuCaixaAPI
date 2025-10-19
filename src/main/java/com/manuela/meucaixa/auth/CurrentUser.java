package com.manuela.meucaixa.auth;

public interface CurrentUser {
    String customerCode();

    String subject();

    String email();

    String name();

    String username();

    String remoteAddress();

    String userAgent();
}
