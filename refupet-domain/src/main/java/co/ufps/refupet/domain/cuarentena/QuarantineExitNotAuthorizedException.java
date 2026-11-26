package co.ufps.refupet.domain.cuarentena;

import co.ufps.refupet.domain.DomainException;

/**
 * La salida de cuarentena no ha sido autorizada por un veterinario.
 */
public class QuarantineExitNotAuthorizedException extends DomainException {
    public QuarantineExitNotAuthorizedException(String message) {
        super(message);
    }
}
