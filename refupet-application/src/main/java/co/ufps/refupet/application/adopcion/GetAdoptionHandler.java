package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetAdoptionHandler {

    private final AdopcionService adopciones;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public GetAdoptionHandler(AdopcionService adopciones, ValidateRolePermissionHandler permisos) {
        this.adopciones = adopciones;
        this.permisos = permisos;
    }

    public AdopcionDTO execute(GetAdoptionQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.GESTIONAR_ADOPCIONES));
        return AdopcionDTO.from(adopciones.obtenerAdopcion(query.toAdopcionId()));
    }
}
