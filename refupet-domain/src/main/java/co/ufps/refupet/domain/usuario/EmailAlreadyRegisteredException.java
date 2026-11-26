package co.ufps.refupet.domain.usuario;

import co.ufps.refupet.domain.DomainException;

/**
 * Ya existe un usuario registrado con el email indicado.
 */
public class EmailAlreadyRegisteredException extends DomainException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
