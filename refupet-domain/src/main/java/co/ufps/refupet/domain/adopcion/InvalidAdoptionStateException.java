package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.DomainException;

/**
 * Operacion no permitida por el estado de una adopcion formalizada.
 */
public class InvalidAdoptionStateException extends DomainException {
    public InvalidAdoptionStateException(String message) {
        super(message);
    }
}
