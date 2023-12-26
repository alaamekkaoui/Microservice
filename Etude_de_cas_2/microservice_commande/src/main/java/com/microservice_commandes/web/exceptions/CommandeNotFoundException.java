package com.microservice_commandes.web.exceptions;

public class CommandeNotFoundException extends Throwable {
    String error;

   public CommandeNotFoundException(String error)
    {
        this.error=error;
    }
}
