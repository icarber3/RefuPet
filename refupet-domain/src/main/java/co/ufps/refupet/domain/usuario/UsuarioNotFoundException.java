package co.ufps.refupet.domain.usuario;

import co.ufps.refupet.domain.ResourceNotFoundException;


/**
 * No existe Usuario con el identificador indicado.
 */
public class UsuarioNotFoundException extends ResourceNotFoundException {
    public UsuarioNotFoundException(UsuarioId id) {
        super("Usuario", id.value());
    }
}
