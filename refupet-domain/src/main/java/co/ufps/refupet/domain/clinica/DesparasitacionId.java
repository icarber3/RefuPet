package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de una desparasitacion.
 */
public final class DesparasitacionId extends Identificador {

    private DesparasitacionId(String value) {
        super(value, "desparasitacionId");
    }

    public static DesparasitacionId generate() {
        return new DesparasitacionId(nuevoValor());
    }

    public static DesparasitacionId of(String value) {
        return new DesparasitacionId(value);
    }
}
