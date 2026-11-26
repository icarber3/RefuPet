package co.ufps.refupet.domain.animal;

import co.ufps.refupet.domain.DomainException;

/**
 * Operacion no permitida por el estado actual del animal (ubicacion, esterilizacion, disponibilidad).
 */
public class InvalidAnimalStateException extends DomainException {
    public InvalidAnimalStateException(String message) {
        super(message);
    }
}
