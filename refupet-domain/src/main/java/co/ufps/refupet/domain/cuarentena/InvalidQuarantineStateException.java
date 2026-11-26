package co.ufps.refupet.domain.cuarentena;

import co.ufps.refupet.domain.DomainException;

/**
 * Transicion no permitida en el ciclo de vida de una cuarentena.
 */
public class InvalidQuarantineStateException extends DomainException {
    public InvalidQuarantineStateException(String message) {
        super(message);
    }
}
