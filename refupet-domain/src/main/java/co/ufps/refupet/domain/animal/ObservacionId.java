package co.ufps.refupet.domain.animal;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de una observacion de comportamiento.
 */
public final class ObservacionId extends Identificador {

    private ObservacionId(String value) {
        super(value, "observacionId");
    }

    public static ObservacionId generate() {
        return new ObservacionId(nuevoValor());
    }

    public static ObservacionId of(String value) {
        return new ObservacionId(value);
    }
}
