package com.axgrid.session.exception;

public class AxSessionExpiredException extends RuntimeException {
    public AxSessionExpiredException(String session) {
        super(String.format("Session %s expired", session));
    }
}
