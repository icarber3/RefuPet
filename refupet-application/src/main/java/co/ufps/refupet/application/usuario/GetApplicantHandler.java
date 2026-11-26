package co.ufps.refupet.application.usuario;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.InvalidDomainDataException;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Solicitante;
import co.ufps.refupet.domain.usuario.Usuario;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.domain.usuario.UsuarioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Regla 20: el administrador consulta la información de un solicitante
 * (por ejemplo, al revisar una solicitud de adopción).
 */
@ApplicationScoped
public class GetApplicantHandler {

    private final IUsuarioRepository usuarioRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public GetApplicantHandler(IUsuarioRepository usuarioRepository, ValidateRolePermissionHandler permisos) {
        this.usuarioRepository = usuarioRepository;
        this.permisos = permisos;
    }

    public SolicitanteDTO execute(GetApplicantQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.GESTIONAR_SOLICITUDES_ADOPCION));
        UsuarioId solicitanteId = query.toSolicitanteId();
        Usuario usuario = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new UsuarioNotFoundException(solicitanteId));
        if (!(usuario instanceof Solicitante solicitante)) {
            throw new InvalidDomainDataException("El usuario " + solicitanteId.value() + " no es un solicitante");
        }
        return SolicitanteDTO.from(solicitante);
    }
}
