package co.ufps.refupet.application.animal;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.AnimalNotFoundException;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Regla 3: visualizar la información general de un animal.
 */
@ApplicationScoped
public class GetAnimalHandler {

    private final IAnimalRepository animalRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public GetAnimalHandler(IAnimalRepository animalRepository, ValidateRolePermissionHandler permisos) {
        this.animalRepository = animalRepository;
        this.permisos = permisos;
    }

    public AnimalDTO execute(GetAnimalQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.CONSULTAR_ANIMALES));
        AnimalId animalId = query.toAnimalId();
        return animalRepository.findById(animalId)
                .map(AnimalDTO::from)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
    }
}
