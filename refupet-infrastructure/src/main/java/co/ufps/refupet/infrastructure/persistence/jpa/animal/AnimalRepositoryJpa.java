package co.ufps.refupet.infrastructure.persistence.jpa.animal;

import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.EstadoUbicacionAnimal;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador JPA de {@link IAnimalRepository}. El agregado se carga siempre con sus observaciones
 * en una sola consulta ({@code LEFT JOIN FETCH}) para evitar consultas N+1 y cargas diferidas
 * fuera de la sesión.
 */
@ApplicationScoped
public class AnimalRepositoryJpa implements IAnimalRepository {

    @Inject
    EntityManager entityManager;

    /** Inserta o actualiza el animal; las observaciones nuevas se insertan por cascada (MERGE). */
    @Override
    @Transactional
    public Animal save(Animal animal) {
        entityManager.merge(AnimalMapper.toJpa(animal));
        return animal;
    }

    @Override
    public Optional<Animal> findById(AnimalId animalId) {
        return entityManager.createNamedQuery(AnimalJpa.POR_ID, AnimalJpa.class)
                .setParameter("id", animalId.value())
                .getResultStream()
                .findFirst()
                .map(AnimalMapper::toDomain);
    }

    @Override
    public boolean existsById(AnimalId animalId) {
        Long total = entityManager.createNamedQuery(AnimalJpa.CONTAR_POR_ID, Long.class)
                .setParameter("id", animalId.value())
                .getSingleResult();
        return total > 0;
    }

    /** Orden estable por fecha de ingreso (el esquema no guarda el instante de inserción). */
    @Override
    public List<Animal> findAll() {
        return entityManager.createNamedQuery(AnimalJpa.TODOS, AnimalJpa.class)
                .getResultList()
                .stream()
                .map(AnimalMapper::toDomain)
                .toList();
    }

    @Override
    public List<Animal> findByUbicacion(EstadoUbicacionAnimal ubicacion) {
        return entityManager.createNamedQuery(AnimalJpa.POR_UBICACION, AnimalJpa.class)
                .setParameter("ubicacion", ubicacion)
                .getResultList()
                .stream()
                .map(AnimalMapper::toDomain)
                .toList();
    }
}
