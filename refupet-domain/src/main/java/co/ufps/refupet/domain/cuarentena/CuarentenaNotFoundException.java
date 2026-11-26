package co.ufps.refupet.domain.cuarentena;

import co.ufps.refupet.domain.ResourceNotFoundException;


/**
 * No existe Cuarentena con el identificador indicado.
 */
public class CuarentenaNotFoundException extends ResourceNotFoundException {
    public CuarentenaNotFoundException(CuarentenaId id) {
        super("Cuarentena", id.value());
    }
}
