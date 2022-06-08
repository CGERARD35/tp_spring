package projetPOEIspring.poeidata.exceptions;

public class UnknownResourceException extends RuntimeException{

    public UnknownResourceException() {
        super("Unkwon ressouce");
    }

    public UnknownResourceException(String message) {
        super(message);
    }
}
