package co.ufps.refupet.application.cuarentena;

import co.ufps.refupet.application.FechaOperacion;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.AnimalNotFoundException;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.cuarentena.Cuarentena;
import co.ufps.refupet.domain.cuarentena.ICuarentenaRepository;
import co.ufps.refupet.domain.cuarentena.InvalidQuarantineStateException;
import co.ufps.refupet.domain.cuarentena.PeriodoCuarentena;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Clock;

/**
 * Regla 12: registrar el inicio de la cuarentena de un animal.
 * Coordina dos agregados: crea la Cuarentena y ubica al Animal en cuarentena.
 * Un animal no puede tener dos cuarentenas activas a la vez.
 */
@ApplicationScoped
public class StartQuarantineHandler {

    private final ICuarentenaRepository cuarentenaRepository;
    private final IAnimalRepository animalRepository;
    private final CuarentenaService cuarentenas;
    private final ValidateRolePermissionHandler permisos;
    private final Clock clock;

    @Inject
    public StartQuarantineHandler(ICuarentenaRepository cuarentenaRepository, IAnimalRepository animalRepository,
                                  CuarentenaService cuarentenas, ValidateRolePermissionHandler permisos, Clock clock) {
        this.cuarentenaRepository = cuarentenaRepository;
        this.animalRepository = animalRepository;
        this.cuarentenas = cuarentenas;
        this.permisos = permisos;
        this.clock = clock;
    }

    @Transactional
    public CuarentenaDTO execute(StartQuarantineCommand command) {
        Usuario responsable = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.GESTIONAR_CUARENTENA));

        AnimalId animalId = command.toAnimalId();
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
        if (cuarentenas.activaDe(animalId).isPresent()) {
            throw new InvalidQuarantineStateException("El animal " + animal.getNombre() + " ya tiene una cuarentena activa");
        }

        PeriodoCuarentena periodo = new PeriodoCuarentena(
                FechaOperacion.resolver(command.fechaInicio(), clock, "fecha de inicio de la cuarentena"),
                command.diasMinimos());
        Cuarentena cuarentena = Cuarentena.iniciar(animalId, periodo, responsable.getUsuarioId());
        animal.ingresarACuarentena();

        animalRepository.save(animal);
        cuarentenaRepository.save(cuarentena);
        return cuarentenas.toDTO(cuarentena);
    }
}
