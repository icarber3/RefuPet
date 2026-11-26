package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.ResourceNotFoundException;


/**
 * No existe Adopcion con el identificador indicado.
 */
public class AdopcionNotFoundException extends ResourceNotFoundException {
    public AdopcionNotFoundException(AdopcionId id) {
        super("Adopcion", id.value());
    }
}
