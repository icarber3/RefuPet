package co.ufps.refupet.domain.animal;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia del agregado Animal (incluye sus observaciones de comportamiento).
 */
public interface IAnimalRepository {
    Animal save(Animal animal);

    Optional<Animal> findById(AnimalId animalId);

    boolean existsById(AnimalId animalId);

    List<Animal> findAll();

    List<Animal> findByUbicacion(EstadoUbicacionAnimal ubicacion);
}
