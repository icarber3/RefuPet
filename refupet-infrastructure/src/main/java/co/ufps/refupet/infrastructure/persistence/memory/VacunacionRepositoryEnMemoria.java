package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.IVacunacionRepository;
import co.ufps.refupet.domain.clinica.Vacunacion;
import co.ufps.refupet.domain.clinica.VacunacionId;

import java.util.List;

/**
 * Adaptador en memoria de IVacunacionRepository.
 */
public class VacunacionRepositoryEnMemoria extends RepositorioEnMemoria<VacunacionId, Vacunacion>
        implements IVacunacionRepository {

    public VacunacionRepositoryEnMemoria() {
        super(Vacunacion::getId);
    }

    @Override
    public Vacunacion save(Vacunacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Vacunacion> findByAnimalId(AnimalId animalId) {
        return filtrar(r -> r.getAnimalId().equals(animalId));
    }
}
