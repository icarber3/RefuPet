package co.ufps.refupet.application.commands.seguridad;

import co.ufps.refupet.application.Entrada;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.UsuarioId;

/**
 * Comando para verificar que el usuario que ejecuta un caso de uso puede realizar la operación.
 */
public record ValidateRolePermissionCommand(String actorId, Operacion operacion) {
    public ValidateRolePermissionCommand {
        Entrada.requerido(actorId, "actorId");
        Entrada.requerido(operacion, "operacion");
    }

    public UsuarioId toActorId() {
        return UsuarioId.of(actorId);
    }
}
