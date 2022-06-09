package projetPOEIspring.poeidata.exceptions;

public class NotAllowedToDeleteManagerException extends RuntimeException{

    public NotAllowedToDeleteManagerException(String message) {
        super (message);
    }

    public NotAllowedToDeleteManagerException() {
        super ("Cannot delete the given manager.");
    }
}
