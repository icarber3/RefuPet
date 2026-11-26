package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Regla 21: registrar la evaluación básica de compatibilidad de una solicitud pendiente.
 */
@ApplicationScoped
public class EvaluateBasicCompatibilityHandler {

    private final ISolicitudAdopcionRepository solicitudRepository;
    private final AdopcionService adopciones;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public EvaluateBasicCompatibilityHandler(ISolicitudAdopcionRepository solicitudRepository,
                                             AdopcionService adopciones, ValidateRolePermissionHandler permisos) {
        this.solicitudRepository = solicitudRepository;
        this.adopciones = adopciones;
        this.permisos = permisos;
    }

    @Transactional
    public SolicitudAdopcionDTO execute(EvaluateBasicCompatibilityCommand command) {
        Usuario evaluador = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.GESTIONAR_SOLICITUDES_ADOPCION));

        SolicitudAdopcion solicitud = adopciones.obtenerSolicitud(command.toSolicitudId());
        solicitud.evaluarCompatibilidad(command.compatible(), command.observaciones(), evaluador.getUsuarioId());
        return SolicitudAdopcionDTO.from(solicitudRepository.save(solicitud));
    }
}
