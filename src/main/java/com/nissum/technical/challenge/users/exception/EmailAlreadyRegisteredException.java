package com.nissum.technical.challenge.users.exception;

public class EmailAlreadyRegisteredException extends Exception {

    public EmailAlreadyRegisteredException() {
        super("El correo ya registrado");
    }
}