package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.adopcion.InvalidAdoptionRequestStateException;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Reglas 19 y 38: el solicitante presenta una solicitud para un animal disponible.
 * No se admite una segunda solicitud pendiente del mismo solicitante para el mismo animal.
 */
@ApplicationScoped
public class SubmitAdoptionRequestHandler {

    private final ISolicitudAdopcionRepository solicitudRepository;
    private final AdopcionService adopciones;
    private final DisponibilidadAdopcionService disponibilidad;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public SubmitAdoptionRequestHandler(ISolicitudAdopcionRepository solicitudRepository, AdopcionService adopciones,
                                        DisponibilidadAdopcionService disponibilidad,
                                        ValidateRolePermissionHandler permisos) {
        this.solicitudRepository = solicitudRepository;
        this.adopciones = adopciones;
        this.disponibilidad = disponibilidad;
        this.permisos = permisos;
    }

    @Transactional
    public SolicitudAdopcionDTO execute(SubmitAdoptionRequestCommand command) {
        Usuario solicitante = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.PRESENTAR_SOLICITUD_ADOPCION));

        Animal animal = adopciones.obtenerAnimal(command.toAnimalId());
        disponibilidad.exigirDisponible(animal);

        boolean yaTienePendiente = solicitudRepository.findBySolicitanteId(solicitante.getUsuarioId()).stream()
                .anyMatch(s -> s.getAnimalId().equals(animal.getAnimalId()) && s.estaPendiente());
        if (yaTienePendiente) {
            throw new InvalidAdoptionRequestStateException(
                    "Ya existe una solicitud pendiente de este solicitante para el animal " + animal.getNombre());
        }

        SolicitudAdopcion solicitud = SolicitudAdopcion.create(
                animal.getAnimalId(), solicitante.getUsuarioId(), command.comentarios());
        return SolicitudAdopcionDTO.from(solicitudRepository.save(solicitud));
    }
}
