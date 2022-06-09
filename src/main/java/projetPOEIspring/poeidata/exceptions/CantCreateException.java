package projetPOEIspring.poeidata.exceptions;

public class CantCreateException extends RuntimeException{

    public CantCreateException(String message){
        super(message);
    }

    public CantCreateException() {
        super("Can't create the given element");
    }
}
