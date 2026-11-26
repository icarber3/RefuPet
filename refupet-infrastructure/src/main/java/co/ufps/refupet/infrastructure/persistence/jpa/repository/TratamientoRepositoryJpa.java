package co.ufps.refupet.infrastructure.persistence.jpa.repository;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.clinica.ITratamientoRepository;
import co.ufps.refupet.domain.clinica.Tratamiento;
import co.ufps.refupet.infrastructure.persistence.jpa.entity.TratamientoJpa;
import co.ufps.refupet.infrastructure.persistence.jpa.mapper.RegistroClinicoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/** Adaptador JPA de {@link ITratamientoRepository}. */
@ApplicationScoped
public class TratamientoRepositoryJpa extends RegistroClinicoRepositoryJpa<Tratamiento, TratamientoJpa>
        implements ITratamientoRepository {

    public TratamientoRepositoryJpa() {
        super(TratamientoJpa.class, RegistroClinicoMapper::toJpa, RegistroClinicoMapper::toDomain);
    }

    @Override
    @Transactional
    public Tratamiento save(Tratamiento registro) {
        return guardar(registro);
    }

    @Override
    public List<Tratamiento> findByAnimalId(AnimalId animalId) {
        return buscarPorAnimal(animalId);
    }
}
