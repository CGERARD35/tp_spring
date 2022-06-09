package projetPOEIspring.poeidata.exceptions;

public class PhoneException extends RuntimeException{

    public PhoneException() {
        super("Phone number is invalid");
    }

    public PhoneException(String message) {
        super(message);
    }
}
