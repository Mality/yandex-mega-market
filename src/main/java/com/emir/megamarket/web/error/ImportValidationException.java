package com.emir.megamarket.web.error;

public class ImportValidationException extends RuntimeException {

    public ImportValidationException() {
        super();
    }

    public ImportValidationException(String message) {
        super(message);
    }
}
