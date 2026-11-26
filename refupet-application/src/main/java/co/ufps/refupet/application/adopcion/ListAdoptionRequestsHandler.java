package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ListAdoptionRequestsHandler {

    private final ISolicitudAdopcionRepository solicitudRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public ListAdoptionRequestsHandler(ISolicitudAdopcionRepository solicitudRepository,
                                       ValidateRolePermissionHandler permisos) {
        this.solicitudRepository = solicitudRepository;
        this.permisos = permisos;
    }

    public List<SolicitudAdopcionDTO> execute(ListAdoptionRequestsQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.GESTIONAR_SOLICITUDES_ADOPCION));
        List<SolicitudAdopcion> solicitudes = query.toEstado()
                .map(solicitudRepository::findByEstado)
                .orElseGet(solicitudRepository::findAll);
        return solicitudes.stream().map(SolicitudAdopcionDTO::from).toList();
    }
}
