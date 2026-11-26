package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.Esterilizacion;
import co.ufps.refupet.domain.clinica.EsterilizacionId;
import co.ufps.refupet.domain.clinica.IEsterilizacionRepository;

import java.util.List;

/**
 * Adaptador en memoria de IEsterilizacionRepository.
 */
public class EsterilizacionRepositoryEnMemoria extends RepositorioEnMemoria<EsterilizacionId, Esterilizacion>
        implements IEsterilizacionRepository {

    public EsterilizacionRepositoryEnMemoria() {
        super(Esterilizacion::getId);
    }

    @Override
    public Esterilizacion save(Esterilizacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Esterilizacion> findByAnimalId(AnimalId animalId) {
        return filtrar(r -> r.getAnimalId().equals(animalId));
    }
}
