package co.ufps.refupet.domain.usuario;

import co.ufps.refupet.domain.Identificador;

/**
 * Identificador inmutable de un usuario.
 */
public final class UsuarioId extends Identificador {

    private UsuarioId(String value) {
        super(value, "usuarioId");
    }

    public static UsuarioId generate() {
        return new UsuarioId(nuevoValor());
    }

    public static UsuarioId of(String value) {
        return new UsuarioId(value);
    }
}
