package co.ufps.refupet.application.usuario;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.domain.usuario.UsuarioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetUserHandler {

    private final IUsuarioRepository usuarioRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public GetUserHandler(IUsuarioRepository usuarioRepository, ValidateRolePermissionHandler permisos) {
        this.usuarioRepository = usuarioRepository;
        this.permisos = permisos;
    }

    public UsuarioDTO execute(GetUserQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.GESTIONAR_USUARIOS));
        UsuarioId usuarioId = query.toUsuarioId();
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioDTO::from)
                .orElseThrow(() -> new UsuarioNotFoundException(usuarioId));
    }
}
