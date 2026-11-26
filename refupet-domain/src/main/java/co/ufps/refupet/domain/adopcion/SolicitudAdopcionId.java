package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de una solicitud de adopcion.
 */
public final class SolicitudAdopcionId extends Identificador {

    private SolicitudAdopcionId(String value) {
        super(value, "solicitudAdopcionId");
    }

    public static SolicitudAdopcionId generate() {
        return new SolicitudAdopcionId(nuevoValor());
    }

    public static SolicitudAdopcionId of(String value) {
        return new SolicitudAdopcionId(value);
    }
}
