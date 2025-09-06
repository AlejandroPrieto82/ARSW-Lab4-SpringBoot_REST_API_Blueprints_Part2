package edu.eci.arsw.blueprints.persistence;

import org.springframework.stereotype.Service;

/**
 * Excepción lanzada cuando ocurre un error al
 * persistir un plano en el sistema.
 *
 * Ejemplos de errores:
 * - Intentar guardar un plano duplicado.
 * - Fallos internos de la capa de persistencia.
 */
@Service
public class BlueprintPersistenceException extends Exception {

    /**
     * Crea una nueva excepción con un mensaje descriptivo.
     *
     * @param message mensaje que describe la causa del error
     */
    public BlueprintPersistenceException(String message) {
        super(message);
    }

    /**
     * Crea una nueva excepción con un mensaje descriptivo y una causa anidada.
     *
     * @param message mensaje que describe la causa del error
     * @param cause excepción original que causó el error
     */
    public BlueprintPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
