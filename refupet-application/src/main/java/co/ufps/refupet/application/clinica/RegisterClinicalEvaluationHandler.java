package co.ufps.refupet.application.clinica;

import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.clinica.EvaluacionClinica;
import co.ufps.refupet.domain.clinica.IEvaluacionClinicaRepository;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Regla 6: registrar una evaluación clínica, que queda en el historial del animal atendido.
 */
@ApplicationScoped
public class RegisterClinicalEvaluationHandler {

    private final IEvaluacionClinicaRepository evaluacionRepository;
    private final AtencionVeterinariaService atencion;

    @Inject
    public RegisterClinicalEvaluationHandler(IEvaluacionClinicaRepository evaluacionRepository,
                                             AtencionVeterinariaService atencion) {
        this.evaluacionRepository = evaluacionRepository;
        this.atencion = atencion;
    }

    @Transactional
    public EvaluacionClinicaDTO execute(RegisterClinicalEvaluationCommand command) {
        Usuario veterinario = atencion.verificarVeterinario(command.actorId());
        Animal animal = atencion.obtenerAnimal(command.toAnimalId());

        EvaluacionClinica evaluacion = EvaluacionClinica.create(
                animal.getAnimalId(), veterinario.getUsuarioId(), atencion.fechaDelRegistro(command.fecha()),
                command.hallazgos(), command.diagnostico(), command.observaciones());
        return EvaluacionClinicaDTO.from(evaluacionRepository.save(evaluacion));
    }
}
