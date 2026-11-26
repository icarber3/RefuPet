package co.ufps.refupet.application.animal;

import co.ufps.refupet.application.FechaOperacion;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.animal.RegistroIngreso;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Reglas 1, 5 y 35: el voluntario registra el ingreso de un animal; queda la
 * trazabilidad de quién, cuándo y en qué condiciones ingresó.
 */
@ApplicationScoped
public class RegisterAnimalHandler {

    private final IAnimalRepository animalRepository;
    private final ValidateRolePermissionHandler permisos;
    private final Clock clock;

    @Inject
    public RegisterAnimalHandler(IAnimalRepository animalRepository, ValidateRolePermissionHandler permisos, Clock clock) {
        this.animalRepository = animalRepository;
        this.permisos = permisos;
        this.clock = clock;
    }

    @Transactional
    public AnimalDTO execute(RegisterAnimalCommand command) {
        Usuario voluntario = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.REGISTRAR_INGRESO_ANIMAL));

        LocalDate fechaIngreso = FechaOperacion.resolver(command.fechaIngreso(), clock, "fecha de ingreso");
        RegistroIngreso ingreso = new RegistroIngreso(
                fechaIngreso, voluntario.getUsuarioId(), command.procedencia(), command.condicionInicial());

        Animal animal = Animal.create(command.nombre(), command.toEspecie(), command.raza(),
                command.descripcion(), ingreso);
        return AnimalDTO.from(animalRepository.save(animal));
    }
}
