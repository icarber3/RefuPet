package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.animal.AnimalDTO;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Reglas 17 y 37: consultar los animales disponibles para adopción.
 */
@ApplicationScoped
public class GetAvailableAnimalsHandler {

    private final DisponibilidadAdopcionService disponibilidad;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public GetAvailableAnimalsHandler(DisponibilidadAdopcionService disponibilidad, ValidateRolePermissionHandler permisos) {
        this.disponibilidad = disponibilidad;
        this.permisos = permisos;
    }

    public List<AnimalDTO> execute(GetAvailableAnimalsQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.CONSULTAR_ANIMALES_DISPONIBLES));
        return disponibilidad.animalesDisponibles().stream().map(AnimalDTO::from).toList();
    }
}
