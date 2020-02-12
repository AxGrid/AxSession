package com.axgrid.session.exception;

public class AxSessionNotFoundException extends RuntimeException {
    public AxSessionNotFoundException(String session) { super(String.format("Session %s not found", session)); }
}