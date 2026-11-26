package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.adopcion.Adopcion;
import co.ufps.refupet.domain.adopcion.AdopcionId;
import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcionId;
import co.ufps.refupet.domain.animal.AnimalId;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador en memoria de IAdopcionRepository.
 */
public class AdopcionRepositoryEnMemoria extends RepositorioEnMemoria<AdopcionId, Adopcion>
        implements IAdopcionRepository {

    public AdopcionRepositoryEnMemoria() {
        super(Adopcion::getAdopcionId);
    }

    @Override
    public Adopcion save(Adopcion adopcion) {
        return guardar(adopcion);
    }

    @Override
    public Optional<Adopcion> findById(AdopcionId adopcionId) {
        return buscarPorId(adopcionId);
    }

    @Override
    public Optional<Adopcion> findBySolicitudId(SolicitudAdopcionId solicitudId) {
        return filtrar(a -> a.getSolicitudId().equals(solicitudId)).stream().findFirst();
    }

    @Override
    public List<Adopcion> findByAnimalId(AnimalId animalId) {
        return filtrar(a -> a.getAnimalId().equals(animalId));
    }
}
