package co.ufps.refupet.application.animal;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.AnimalNotFoundException;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.animal.ObservacionComportamiento;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Reglas 4 y 36: el voluntario registra una observación de comportamiento del animal.
 */
@ApplicationScoped
public class RegisterBehaviorObservationHandler {

    private final IAnimalRepository animalRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public RegisterBehaviorObservationHandler(IAnimalRepository animalRepository, ValidateRolePermissionHandler permisos) {
        this.animalRepository = animalRepository;
        this.permisos = permisos;
    }

    @Transactional
    public ObservacionComportamientoDTO execute(RegisterBehaviorObservationCommand command) {
        Usuario voluntario = permisos.execute(new ValidateRolePermissionCommand(
                command.actorId(), Operacion.REGISTRAR_OBSERVACION_COMPORTAMIENTO));

        AnimalId animalId = command.toAnimalId();
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        ObservacionComportamiento observacion =
                animal.registrarObservacion(command.descripcion(), voluntario.getUsuarioId());
        animalRepository.save(animal);
        return ObservacionComportamientoDTO.from(observacion);
    }
}
