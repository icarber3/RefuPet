package co.ufps.refupet.application.handlers.adopcion;

import co.ufps.refupet.application.dto.AnimalDTO;
import co.ufps.refupet.application.dto.SolicitanteDTO;
import co.ufps.refupet.application.dto.SolicitudAdopcionDTO;
import co.ufps.refupet.application.dto.SolicitudAdopcionDetalleDTO;
import co.ufps.refupet.application.queries.adopcion.GetAdoptionRequestQuery;
import co.ufps.refupet.application.services.AdopcionService;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Solicitante;
import co.ufps.refupet.domain.usuario.Usuario;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.domain.usuario.UsuarioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Regla 20: consultar la solicitud junto con la información del solicitante y del animal.
 * Pueden consultarla el administrador o el propio solicitante que la presentó.
 */
@ApplicationScoped
public class GetAdoptionRequestHandler {

    private final IUsuarioRepository usuarioRepository;
    private final AdopcionService adopciones;

    @Inject
    public GetAdoptionRequestHandler(IUsuarioRepository usuarioRepository, AdopcionService adopciones) {
        this.usuarioRepository = usuarioRepository;
        this.adopciones = adopciones;
    }

    public SolicitudAdopcionDetalleDTO execute(GetAdoptionRequestQuery query) {
        UsuarioId actorId = UsuarioId.of(query.actorId());
        Usuario actor = usuarioRepository.findById(actorId)
                .orElseThrow(() -> new UsuarioNotFoundException(actorId));
        SolicitudAdopcion solicitud = adopciones.obtenerSolicitud(query.toSolicitudId());

        boolean esSuPropiaSolicitud = actor.estaActiva() && actor.getUsuarioId().equals(solicitud.getSolicitanteId());
        if (!esSuPropiaSolicitud) {
            actor.verificarPermiso(Operacion.GESTIONAR_SOLICITUDES_ADOPCION);
        }

        SolicitanteDTO solicitante = usuarioRepository.findById(solicitud.getSolicitanteId())
                .filter(Solicitante.class::isInstance)
                .map(Solicitante.class::cast)
                .map(SolicitanteDTO::from)
                .orElseThrow(() -> new UsuarioNotFoundException(solicitud.getSolicitanteId()));
        AnimalDTO animal = AnimalDTO.from(adopciones.obtenerAnimal(solicitud.getAnimalId()));

        return new SolicitudAdopcionDetalleDTO(SolicitudAdopcionDTO.from(solicitud), solicitante, animal);
    }
}
