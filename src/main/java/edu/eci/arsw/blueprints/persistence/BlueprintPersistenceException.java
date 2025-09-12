package edu.eci.arsw.blueprints.persistence;

/**
 * Excepción lanzada cuando ocurre un error al persistir un plano.
 */
public class BlueprintPersistenceException extends Exception {

    public BlueprintPersistenceException(String message) {
        super(message);
    }

    public BlueprintPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
