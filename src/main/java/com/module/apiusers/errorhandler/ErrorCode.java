package com.module.apiusers.errorhandler;

public enum ErrorCode {

    INTERNAL_ERROR(100, "Error interno del servidor"),
    INVALID_EMAIL(103, "El email ya esta en uso"),
    WRONG_LOGIN_DATA(103, "La combinación de datos no es correcta"),
    AUTHENTICATION_INVALID(104, "Autenticación incorrecta"),
    BAD_REQUEST(105, "Algunos campos no son correctos"),
    UNAUTHORIZED(106, "El token no es valido"),
    CONFLICT(107, "Ya existe un usuario con esos parametros");

    private final int value;
    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
