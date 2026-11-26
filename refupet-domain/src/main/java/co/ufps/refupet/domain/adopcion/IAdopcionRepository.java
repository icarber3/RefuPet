package co.ufps.refupet.domain.adopcion;

import co.ufps.refupet.domain.animal.AnimalId;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia de adopciones formalizadas (incluye seguimientos y devolucion).
 */
public interface IAdopcionRepository {
    Adopcion save(Adopcion adopcion);

    Optional<Adopcion> findById(AdopcionId adopcionId);

    Optional<Adopcion> findBySolicitudId(SolicitudAdopcionId solicitudId);

    List<Adopcion> findByAnimalId(AnimalId animalId);
}
