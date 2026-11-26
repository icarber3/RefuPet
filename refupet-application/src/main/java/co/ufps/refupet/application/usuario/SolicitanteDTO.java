package co.ufps.refupet.application.usuario;

import co.ufps.refupet.domain.usuario.Solicitante;

/**
 * Información del solicitante de adopción (regla 20).
 */
public record SolicitanteDTO(
        String usuarioId,
        String nombre,
        String email,
        String telefono,
        String direccion,
        String estadoCuenta
) {
    public static SolicitanteDTO from(Solicitante solicitante) {
        return new SolicitanteDTO(
                solicitante.getUsuarioId().value(),
                solicitante.getNombre(),
                solicitante.getEmail().value(),
                solicitante.getTelefono(),
                solicitante.getDireccion(),
                solicitante.getEstadoCuenta().name());
    }
}
