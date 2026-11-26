package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.Desparasitacion;
import co.ufps.refupet.domain.clinica.DesparasitacionId;
import co.ufps.refupet.domain.clinica.IDesparasitacionRepository;

import java.util.List;

/**
 * Adaptador en memoria de IDesparasitacionRepository.
 */
public class DesparasitacionRepositoryEnMemoria extends RepositorioEnMemoria<DesparasitacionId, Desparasitacion>
        implements IDesparasitacionRepository {

    public DesparasitacionRepositoryEnMemoria() {
        super(Desparasitacion::getId);
    }

    @Override
    public Desparasitacion save(Desparasitacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Desparasitacion> findByAnimalId(AnimalId animalId) {
        return filtrar(r -> r.getAnimalId().equals(animalId));
    }
}
