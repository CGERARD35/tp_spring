package projetPOEIspring.poeidata.exceptions;

public class NameException extends RuntimeException{

    public NameException() {
        super("Invalidate Name");
    }

    public NameException(String message) {
        super(message);
    }
}
