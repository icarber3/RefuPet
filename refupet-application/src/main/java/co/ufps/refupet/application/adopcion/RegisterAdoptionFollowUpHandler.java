package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.FechaOperacion;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.adopcion.Adopcion;
import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SeguimientoAdopcion;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Clock;

/**
 * Regla 24: registrar un seguimiento posterior a la adopción.
 */
@ApplicationScoped
public class RegisterAdoptionFollowUpHandler {

    private final IAdopcionRepository adopcionRepository;
    private final AdopcionService adopciones;
    private final ValidateRolePermissionHandler permisos;
    private final Clock clock;

    @Inject
    public RegisterAdoptionFollowUpHandler(IAdopcionRepository adopcionRepository, AdopcionService adopciones,
                                           ValidateRolePermissionHandler permisos, Clock clock) {
        this.adopcionRepository = adopcionRepository;
        this.adopciones = adopciones;
        this.permisos = permisos;
        this.clock = clock;
    }

    @Transactional
    public SeguimientoAdopcionDTO execute(RegisterAdoptionFollowUpCommand command) {
        Usuario responsable = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.GESTIONAR_ADOPCIONES));

        Adopcion adopcion = adopciones.obtenerAdopcion(command.toAdopcionId());
        SeguimientoAdopcion seguimiento = adopcion.registrarSeguimiento(
                FechaOperacion.resolver(command.fecha(), clock, "fecha del seguimiento"),
                command.observaciones(), responsable.getUsuarioId());
        adopcionRepository.save(adopcion);
        return SeguimientoAdopcionDTO.from(seguimiento);
    }
}
