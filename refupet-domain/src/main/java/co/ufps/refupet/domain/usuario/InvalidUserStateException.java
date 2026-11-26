package co.ufps.refupet.domain.usuario;

import co.ufps.refupet.domain.DomainException;

/**
 * Operacion no permitida por el estado de la cuenta del usuario.
 */
public class InvalidUserStateException extends DomainException {
    public InvalidUserStateException(String message) {
        super(message);
    }
}
