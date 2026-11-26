package co.ufps.refupet.application.usuario;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.EstadoCuenta;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ListPendingUsersHandler {

    private final IUsuarioRepository usuarioRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public ListPendingUsersHandler(IUsuarioRepository usuarioRepository, ValidateRolePermissionHandler permisos) {
        this.usuarioRepository = usuarioRepository;
        this.permisos = permisos;
    }

    public List<UsuarioDTO> execute(ListPendingUsersQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.GESTIONAR_USUARIOS));
        return usuarioRepository.findByEstadoCuenta(EstadoCuenta.PENDIENTE_APROBACION).stream()
                .map(UsuarioDTO::from)
                .toList();
    }
}
