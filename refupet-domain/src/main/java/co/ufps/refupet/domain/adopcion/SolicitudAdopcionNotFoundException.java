package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.ResourceNotFoundException;


/**
 * No existe Solicitud de adopcion con el identificador indicado.
 */
public class SolicitudAdopcionNotFoundException extends ResourceNotFoundException {
    public SolicitudAdopcionNotFoundException(SolicitudAdopcionId id) {
        super("Solicitud de adopcion", id.value());
    }
}
