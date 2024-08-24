package it.gaethanos.Rcon;

import it.gaethanos.Rcon.RconClientException;

public class AuthFailureException extends RconClientException {
    public AuthFailureException() {
        super("Authentication failure");
    }
}
