package co.ufps.refupet.domain.cuarentena;

import co.ufps.refupet.domain.animal.AnimalId;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia de cuarentenas.
 * Decidir cual cuarentena esta activa es regla de dominio (Cuarentena.estaActiva), no del repositorio.
 */
public interface ICuarentenaRepository {
    Cuarentena save(Cuarentena cuarentena);

    Optional<Cuarentena> findById(CuarentenaId cuarentenaId);

    List<Cuarentena> findByAnimalId(AnimalId animalId);
}
