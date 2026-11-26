package co.ufps.refupet.application.clinica;

import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.clinica.ITratamientoRepository;
import co.ufps.refupet.domain.clinica.Tratamiento;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Regla 8: registrar un tratamiento médico.
 */
@ApplicationScoped
public class RegisterTreatmentHandler {

    private final ITratamientoRepository tratamientoRepository;
    private final AtencionVeterinariaService atencion;

    @Inject
    public RegisterTreatmentHandler(ITratamientoRepository tratamientoRepository, AtencionVeterinariaService atencion) {
        this.tratamientoRepository = tratamientoRepository;
        this.atencion = atencion;
    }

    @Transactional
    public TratamientoDTO execute(RegisterTreatmentCommand command) {
        Usuario veterinario = atencion.verificarVeterinario(command.actorId());
        Animal animal = atencion.obtenerAnimal(command.toAnimalId());

        Tratamiento tratamiento = Tratamiento.create(animal.getAnimalId(), veterinario.getUsuarioId(),
                atencion.fechaDelRegistro(command.fechaInicio()), command.descripcion(),
                command.indicaciones(), command.observaciones());
        return TratamientoDTO.from(tratamientoRepository.save(tratamiento));
    }
}
