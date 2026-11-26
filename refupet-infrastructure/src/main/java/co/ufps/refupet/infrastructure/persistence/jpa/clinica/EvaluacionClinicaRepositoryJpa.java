package co.ufps.refupet.infrastructure.persistence.jpa.clinica;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.EvaluacionClinica;
import co.ufps.refupet.domain.clinica.IEvaluacionClinicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/** Adaptador JPA de {@link IEvaluacionClinicaRepository}. */
@ApplicationScoped
public class EvaluacionClinicaRepositoryJpa extends RegistroClinicoRepositoryJpa<EvaluacionClinica, EvaluacionClinicaJpa>
        implements IEvaluacionClinicaRepository {

    public EvaluacionClinicaRepositoryJpa() {
        super(EvaluacionClinicaJpa.class, RegistroClinicoMapper::toJpa, RegistroClinicoMapper::toDomain);
    }

    @Override
    @Transactional
    public EvaluacionClinica save(EvaluacionClinica registro) {
        return guardar(registro);
    }

    @Override
    public List<EvaluacionClinica> findByAnimalId(AnimalId animalId) {
        return buscarPorAnimal(animalId);
    }
}
