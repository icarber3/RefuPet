package co.ufps.refupet.infrastructure.persistence.memory;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.cuarentena.Cuarentena;
import co.ufps.refupet.domain.cuarentena.CuarentenaId;
import co.ufps.refupet.domain.cuarentena.ICuarentenaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador en memoria de ICuarentenaRepository.
 */
public class CuarentenaRepositoryEnMemoria extends RepositorioEnMemoria<CuarentenaId, Cuarentena>
        implements ICuarentenaRepository {

    public CuarentenaRepositoryEnMemoria() {
        super(Cuarentena::getCuarentenaId);
    }

    @Override
    public Cuarentena save(Cuarentena cuarentena) {
        return guardar(cuarentena);
    }

    @Override
    public Optional<Cuarentena> findById(CuarentenaId cuarentenaId) {
        return buscarPorId(cuarentenaId);
    }

    @Override
    public List<Cuarentena> findByAnimalId(AnimalId animalId) {
        return filtrar(c -> c.getAnimalId().equals(animalId));
    }
}
