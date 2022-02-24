package com.cscb634.ejournal.constants;

public enum ErrorMessageTemplate {

    ENTITY_ID_DOES_NOT_EXIST("Entity with id %s does not exist"),
    ENTITY_ID_ALREADY_EXIST("Entity with id %s already exist");

    private final String message;

    ErrorMessageTemplate(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }

}