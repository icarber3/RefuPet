package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.adopcion.EstadoSolicitudAdopcion;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcionId;
import co.ufps.refupet.domain.usuario.UsuarioId;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador en memoria de ISolicitudAdopcionRepository.
 */
public class SolicitudAdopcionRepositoryEnMemoria extends RepositorioEnMemoria<SolicitudAdopcionId, SolicitudAdopcion>
        implements ISolicitudAdopcionRepository {

    public SolicitudAdopcionRepositoryEnMemoria() {
        super(SolicitudAdopcion::getSolicitudId);
    }

    @Override
    public SolicitudAdopcion save(SolicitudAdopcion solicitud) {
        return guardar(solicitud);
    }

    @Override
    public Optional<SolicitudAdopcion> findById(SolicitudAdopcionId solicitudId) {
        return buscarPorId(solicitudId);
    }

    @Override
    public List<SolicitudAdopcion> findAll() {
        return todos();
    }

    @Override
    public List<SolicitudAdopcion> findByEstado(EstadoSolicitudAdopcion estado) {
        return filtrar(s -> s.getEstado() == estado);
    }

    @Override
    public List<SolicitudAdopcion> findBySolicitanteId(UsuarioId solicitanteId) {
        return filtrar(s -> s.getSolicitanteId().equals(solicitanteId));
    }
}
