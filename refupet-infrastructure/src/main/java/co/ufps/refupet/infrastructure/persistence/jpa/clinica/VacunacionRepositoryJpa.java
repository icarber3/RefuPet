package co.ufps.refupet.infrastructure.persistence.jpa.clinica;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.IVacunacionRepository;
import co.ufps.refupet.domain.clinica.Vacunacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/** Adaptador JPA de {@link IVacunacionRepository}. */
@ApplicationScoped
public class VacunacionRepositoryJpa extends RegistroClinicoRepositoryJpa<Vacunacion, VacunacionJpa>
        implements IVacunacionRepository {

    public VacunacionRepositoryJpa() {
        super(VacunacionJpa.class, RegistroClinicoMapper::toJpa, RegistroClinicoMapper::toDomain);
    }

    @Override
    @Transactional
    public Vacunacion save(Vacunacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Vacunacion> findByAnimalId(AnimalId animalId) {
        return buscarPorAnimal(animalId);
    }
}
