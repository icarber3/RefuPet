package co.ufps.refupet.application.clinica;

import co.ufps.refupet.application.FechaOperacion;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.AnimalNotFoundException;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Pasos comunes a todos los registros clínicos (reglas 6 a 9, 33 y 34):
 * verificar que quien registra es un veterinario activo, que el animal existe
 * y resolver la fecha del acto clínico. Evita repetir la misma coordinación en cada handler.
 */
@ApplicationScoped
public class AtencionVeterinariaService {

    private final IAnimalRepository animalRepository;
    private final ValidateRolePermissionHandler permisos;
    private final Clock clock;

    @Inject
    public AtencionVeterinariaService(IAnimalRepository animalRepository,
                                      ValidateRolePermissionHandler permisos, Clock clock) {
        this.animalRepository = animalRepository;
        this.permisos = permisos;
        this.clock = clock;
    }

    public Usuario verificarVeterinario(String actorId) {
        return permisos.execute(new ValidateRolePermissionCommand(actorId, Operacion.GESTIONAR_INFORMACION_CLINICA));
    }

    public Animal obtenerAnimal(AnimalId animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException(animalId));
    }

    public LocalDate fechaDelRegistro(LocalDate fechaIndicada) {
        return FechaOperacion.resolver(fechaIndicada, clock, "fecha del registro clínico");
    }
}
