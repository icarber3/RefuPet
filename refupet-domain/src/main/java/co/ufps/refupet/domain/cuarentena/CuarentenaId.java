package co.ufps.refupet.domain.cuarentena;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de una cuarentena.
 */
public final class CuarentenaId extends Identificador {

    private CuarentenaId(String value) {
        super(value, "cuarentenaId");
    }

    public static CuarentenaId generate() {
        return new CuarentenaId(nuevoValor());
    }

    public static CuarentenaId of(String value) {
        return new CuarentenaId(value);
    }
}
