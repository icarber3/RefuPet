package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.ITratamientoRepository;
import co.ufps.refupet.domain.clinica.Tratamiento;
import co.ufps.refupet.domain.clinica.TratamientoId;

import java.util.List;

/**
 * Adaptador en memoria de ITratamientoRepository.
 */
public class TratamientoRepositoryEnMemoria extends RepositorioEnMemoria<TratamientoId, Tratamiento>
        implements ITratamientoRepository {

    public TratamientoRepositoryEnMemoria() {
        super(Tratamiento::getId);
    }

    @Override
    public Tratamiento save(Tratamiento registro) {
        return guardar(registro);
    }

    @Override
    public List<Tratamiento> findByAnimalId(AnimalId animalId) {
        return filtrar(r -> r.getAnimalId().equals(animalId));
    }
}
