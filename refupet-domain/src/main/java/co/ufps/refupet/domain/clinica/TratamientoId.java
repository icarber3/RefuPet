package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de un tratamiento medico.
 */
public final class TratamientoId extends Identificador {

    private TratamientoId(String value) {
        super(value, "tratamientoId");
    }

    public static TratamientoId generate() {
        return new TratamientoId(nuevoValor());
    }

    public static TratamientoId of(String value) {
        return new TratamientoId(value);
    }
}
