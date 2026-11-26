package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de una vacunacion.
 */
public final class VacunacionId extends Identificador {

    private VacunacionId(String value) {
        super(value, "vacunacionId");
    }

    public static VacunacionId generate() {
        return new VacunacionId(nuevoValor());
    }

    public static VacunacionId of(String value) {
        return new VacunacionId(value);
    }
}
