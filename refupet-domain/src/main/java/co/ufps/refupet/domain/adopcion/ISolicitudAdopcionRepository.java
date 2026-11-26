package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.usuario.UsuarioId;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia de solicitudes de adopcion.
 */
public interface ISolicitudAdopcionRepository {
    SolicitudAdopcion save(SolicitudAdopcion solicitud);

    Optional<SolicitudAdopcion> findById(SolicitudAdopcionId solicitudId);

    List<SolicitudAdopcion> findAll();

    List<SolicitudAdopcion> findByEstado(EstadoSolicitudAdopcion estado);

    List<SolicitudAdopcion> findBySolicitanteId(UsuarioId solicitanteId);
}
