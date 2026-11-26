package co.ufps.refupet.application.adopcion;

import co.ufps.refupet.domain.adopcion.Adopcion;
import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.EstadoUbicacionAnimal;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.animal.InvalidAnimalStateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Determina qué animales están disponibles para adopción (reglas 17 y 37).
 *
 * Coordina dos agregados (Animal y Adopcion), por eso vive en application y no en una entidad:
 * un animal está disponible si ya pasó a población general y no tiene una adopción vigente.
 * Lo usan la consulta de disponibles, la presentación, la aprobación y la formalización,
 * evitando duplicar la regla.
 */
@ApplicationScoped
public class DisponibilidadAdopcionService {

    private final IAnimalRepository animalRepository;
    private final IAdopcionRepository adopcionRepository;

    @Inject
    public DisponibilidadAdopcionService(IAnimalRepository animalRepository, IAdopcionRepository adopcionRepository) {
        this.animalRepository = animalRepository;
        this.adopcionRepository = adopcionRepository;
    }

    public boolean estaDisponible(Animal animal) {
        return animal.estaEnPoblacionGeneral()
                && adopcionRepository.findByAnimalId(animal.getAnimalId()).stream().noneMatch(Adopcion::estaVigente);
    }

    public List<Animal> animalesDisponibles() {
        return animalRepository.findByUbicacion(EstadoUbicacionAnimal.POBLACION_GENERAL).stream()
                .filter(this::estaDisponible)
                .toList();
    }

    public void exigirDisponible(Animal animal) {
        if (!estaDisponible(animal)) {
            throw new InvalidAnimalStateException(
                    "El animal " + animal.getNombre() + " no está disponible para adopción");
        }
    }
}
