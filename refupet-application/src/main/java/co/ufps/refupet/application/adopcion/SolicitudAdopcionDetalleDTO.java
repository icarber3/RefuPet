package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.application.animal.AnimalDTO;
import co.ufps.refupet.application.usuario.SolicitanteDTO;

/**
 * Solicitud junto con la información del solicitante y del animal correspondiente (regla 20).
 */
public record SolicitudAdopcionDetalleDTO(
        SolicitudAdopcionDTO solicitud,
        SolicitanteDTO solicitante,
        AnimalDTO animal
) {
}
