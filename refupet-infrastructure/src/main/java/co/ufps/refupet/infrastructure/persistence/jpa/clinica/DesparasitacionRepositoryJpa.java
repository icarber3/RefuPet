package co.ufps.refupet.infrastructure.persistence.jpa.clinica;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.Desparasitacion;
import co.ufps.refupet.domain.clinica.IDesparasitacionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/** Adaptador JPA de {@link IDesparasitacionRepository}. */
@ApplicationScoped
public class DesparasitacionRepositoryJpa extends RegistroClinicoRepositoryJpa<Desparasitacion, DesparasitacionJpa>
        implements IDesparasitacionRepository {

    public DesparasitacionRepositoryJpa() {
        super(DesparasitacionJpa.class, RegistroClinicoMapper::toJpa, RegistroClinicoMapper::toDomain);
    }

    @Override
    @Transactional
    public Desparasitacion save(Desparasitacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Desparasitacion> findByAnimalId(AnimalId animalId) {
        return buscarPorAnimal(animalId);
    }
}
