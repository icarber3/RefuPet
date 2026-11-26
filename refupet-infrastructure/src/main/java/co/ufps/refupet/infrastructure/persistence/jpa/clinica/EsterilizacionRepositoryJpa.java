package co.ufps.refupet.infrastructure.persistence.jpa.clinica;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.Esterilizacion;
import co.ufps.refupet.domain.clinica.IEsterilizacionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/** Adaptador JPA de {@link IEsterilizacionRepository}. */
@ApplicationScoped
public class EsterilizacionRepositoryJpa extends RegistroClinicoRepositoryJpa<Esterilizacion, EsterilizacionJpa>
        implements IEsterilizacionRepository {

    public EsterilizacionRepositoryJpa() {
        super(EsterilizacionJpa.class, RegistroClinicoMapper::toJpa, RegistroClinicoMapper::toDomain);
    }

    @Override
    @Transactional
    public Esterilizacion save(Esterilizacion registro) {
        return guardar(registro);
    }

    @Override
    public List<Esterilizacion> findByAnimalId(AnimalId animalId) {
        return buscarPorAnimal(animalId);
    }
}
