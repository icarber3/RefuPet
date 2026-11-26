package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.DomainException;

/**
 * Transicion no permitida en una solicitud de adopcion.
 */
public class InvalidAdoptionRequestStateException extends DomainException {
    public InvalidAdoptionRequestStateException(String message) {
        super(message);
    }
}
