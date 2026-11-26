package co.ufps.refupet.infrastructure.config;

import co.ufps.refupet.application.adopcion.AdopcionService;
import co.ufps.refupet.application.adopcion.ApproveAdoptionRequestHandler;
import co.ufps.refupet.application.adopcion.DisponibilidadAdopcionService;
import co.ufps.refupet.application.adopcion.EvaluateBasicCompatibilityHandler;
import co.ufps.refupet.application.adopcion.FormalizeAdoptionHandler;
import co.ufps.refupet.application.adopcion.GetAdoptionHandler;
import co.ufps.refupet.application.adopcion.GetAdoptionRequestHandler;
import co.ufps.refupet.application.adopcion.GetAvailableAnimalsHandler;
import co.ufps.refupet.application.adopcion.ListAdoptionRequestsHandler;
import co.ufps.refupet.application.adopcion.RegisterAdoptionFollowUpHandler;
import co.ufps.refupet.application.adopcion.RegisterAdoptionReturnHandler;
import co.ufps.refupet.application.adopcion.RejectAdoptionRequestHandler;
import co.ufps.refupet.application.adopcion.SubmitAdoptionRequestHandler;
import co.ufps.refupet.application.animal.GetAnimalHandler;
import co.ufps.refupet.application.animal.GetAnimalIngressTraceHandler;
import co.ufps.refupet.application.animal.GetAnimalObservationsHandler;
import co.ufps.refupet.application.animal.ListAnimalsHandler;
import co.ufps.refupet.application.animal.RegisterAnimalHandler;
import co.ufps.refupet.application.animal.RegisterBehaviorObservationHandler;
import co.ufps.refupet.application.clinica.AtencionVeterinariaService;
import co.ufps.refupet.application.clinica.GetClinicalHistoryHandler;
import co.ufps.refupet.application.clinica.RegisterClinicalEvaluationHandler;
import co.ufps.refupet.application.clinica.RegisterDewormingHandler;
import co.ufps.refupet.application.clinica.RegisterSterilizationHandler;
import co.ufps.refupet.application.clinica.RegisterTreatmentHandler;
import co.ufps.refupet.application.clinica.RegisterVaccinationHandler;
import co.ufps.refupet.application.clinica.UpdateAnimalHealthHandler;
import co.ufps.refupet.application.cuarentena.AuthorizeQuarantineExitHandler;
import co.ufps.refupet.application.cuarentena.CheckMinimumQuarantinePeriodHandler;
import co.ufps.refupet.application.cuarentena.CuarentenaService;
import co.ufps.refupet.application.cuarentena.GetAnimalQuarantineStatusHandler;
import co.ufps.refupet.application.cuarentena.GetAnimalsInQuarantineHandler;
import co.ufps.refupet.application.cuarentena.GetQuarantineHandler;
import co.ufps.refupet.application.cuarentena.MoveAnimalToGeneralPopulationHandler;
import co.ufps.refupet.application.cuarentena.StartQuarantineHandler;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.application.usuario.ActivateUserHandler;
import co.ufps.refupet.application.usuario.ApprovePendingUserHandler;
import co.ufps.refupet.application.usuario.CreateUserHandler;
import co.ufps.refupet.application.usuario.GetApplicantHandler;
import co.ufps.refupet.application.usuario.GetUserHandler;
import co.ufps.refupet.application.usuario.ListPendingUsersHandler;
import co.ufps.refupet.application.usuario.RegisterApplicantHandler;
import co.ufps.refupet.application.usuario.SuspendUserHandler;
import co.ufps.refupet.application.usuario.UpdateUserHandler;
import co.ufps.refupet.application.usuario.UsuarioDTO;
import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.clinica.IDesparasitacionRepository;
import co.ufps.refupet.domain.clinica.IEsterilizacionRepository;
import co.ufps.refupet.domain.clinica.IEvaluacionClinicaRepository;
import co.ufps.refupet.domain.clinica.ITratamientoRepository;
import co.ufps.refupet.domain.clinica.IVacunacionRepository;
import co.ufps.refupet.domain.cuarentena.ICuarentenaRepository;
import co.ufps.refupet.domain.usuario.Administrador;
import co.ufps.refupet.domain.usuario.Email;
import co.ufps.refupet.domain.usuario.EmailAlreadyRegisteredException;
import co.ufps.refupet.domain.usuario.EstadoCuenta;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.infrastructure.persistence.memory.AdopcionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.AnimalRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.CuarentenaRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.DesparasitacionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.EsterilizacionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.EvaluacionClinicaRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.SolicitudAdopcionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.TratamientoRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.UsuarioRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.VacunacionRepositoryEnMemoria;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Composition root de RefuPet: crea los repositorios en memoria y ensambla todos los
 * casos de uso mediante inyección por constructor.
 *
 * Decisión: Streamflix delega este ensamblaje en el contenedor CDI de Quarkus. RefuPet no usa
 * frameworks (alcance del enunciado), así que el ensamblaje es explícito aquí, en la capa más
 * externa. Los handlers no cambian: siguen recibiendo sus dependencias por constructor.
 * El reloj se recibe como parámetro para poder controlar el tiempo en las pruebas.
 */
public class RefuPetAplicacion {

    // ── Repositorios (adaptadores en memoria) ──
    private final IUsuarioRepository usuarioRepository = new UsuarioRepositoryEnMemoria();
    private final IAnimalRepository animalRepository = new AnimalRepositoryEnMemoria();
    private final IEvaluacionClinicaRepository evaluacionRepository = new EvaluacionClinicaRepositoryEnMemoria();
    private final IVacunacionRepository vacunacionRepository = new VacunacionRepositoryEnMemoria();
    private final IDesparasitacionRepository desparasitacionRepository = new DesparasitacionRepositoryEnMemoria();
    private final ITratamientoRepository tratamientoRepository = new TratamientoRepositoryEnMemoria();
    private final IEsterilizacionRepository esterilizacionRepository = new EsterilizacionRepositoryEnMemoria();
    private final ICuarentenaRepository cuarentenaRepository = new CuarentenaRepositoryEnMemoria();
    private final ISolicitudAdopcionRepository solicitudRepository = new SolicitudAdopcionRepositoryEnMemoria();
    private final IAdopcionRepository adopcionRepository = new AdopcionRepositoryEnMemoria();

    // ── Usuarios ──
    public final CreateUserHandler createUser;
    public final RegisterApplicantHandler registerApplicant;
    public final UpdateUserHandler updateUser;
    public final SuspendUserHandler suspendUser;
    public final ActivateUserHandler activateUser;
    public final ApprovePendingUserHandler approvePendingUser;
    public final GetUserHandler getUser;
    public final GetApplicantHandler getApplicant;
    public final ListPendingUsersHandler listPendingUsers;

    // ── Animales ──
    public final RegisterAnimalHandler registerAnimal;
    public final GetAnimalHandler getAnimal;
    public final ListAnimalsHandler listAnimals;
    public final RegisterBehaviorObservationHandler registerBehaviorObservation;
    public final GetAnimalObservationsHandler getAnimalObservations;
    public final GetAnimalIngressTraceHandler getAnimalIngressTrace;

    // ── Atención veterinaria ──
    public final RegisterClinicalEvaluationHandler registerClinicalEvaluation;
    public final RegisterVaccinationHandler registerVaccination;
    public final RegisterDewormingHandler registerDeworming;
    public final RegisterTreatmentHandler registerTreatment;
    public final RegisterSterilizationHandler registerSterilization;
    public final UpdateAnimalHealthHandler updateAnimalHealth;
    public final GetClinicalHistoryHandler getClinicalHistory;

    // ── Cuarentena ──
    public final StartQuarantineHandler startQuarantine;
    public final GetAnimalsInQuarantineHandler getAnimalsInQuarantine;
    public final CheckMinimumQuarantinePeriodHandler checkMinimumQuarantinePeriod;
    public final AuthorizeQuarantineExitHandler authorizeQuarantineExit;
    public final MoveAnimalToGeneralPopulationHandler moveAnimalToGeneralPopulation;
    public final GetQuarantineHandler getQuarantine;
    public final GetAnimalQuarantineStatusHandler getAnimalQuarantineStatus;

    // ── Adopción responsable ──
    public final GetAvailableAnimalsHandler getAvailableAnimals;
    public final SubmitAdoptionRequestHandler submitAdoptionRequest;
    public final GetAdoptionRequestHandler getAdoptionRequest;
    public final ListAdoptionRequestsHandler listAdoptionRequests;
    public final EvaluateBasicCompatibilityHandler evaluateBasicCompatibility;
    public final ApproveAdoptionRequestHandler approveAdoptionRequest;
    public final RejectAdoptionRequestHandler rejectAdoptionRequest;
    public final FormalizeAdoptionHandler formalizeAdoption;
    public final RegisterAdoptionFollowUpHandler registerAdoptionFollowUp;
    public final RegisterAdoptionReturnHandler registerAdoptionReturn;
    public final GetAdoptionHandler getAdoption;

    public RefuPetAplicacion() {
        this(Clock.systemDefaultZone());
    }

    public RefuPetAplicacion(Clock clock) {
        ValidateRolePermissionHandler permisos = new ValidateRolePermissionHandler(usuarioRepository);
        AtencionVeterinariaService atencion = new AtencionVeterinariaService(animalRepository, permisos, clock);
        CuarentenaService cuarentenas = new CuarentenaService(cuarentenaRepository, animalRepository, clock);
        DisponibilidadAdopcionService disponibilidad =
                new DisponibilidadAdopcionService(animalRepository, adopcionRepository);
        AdopcionService adopciones = new AdopcionService(solicitudRepository, adopcionRepository, animalRepository);

        createUser = new CreateUserHandler(usuarioRepository, permisos);
        registerApplicant = new RegisterApplicantHandler(usuarioRepository);
        updateUser = new UpdateUserHandler(usuarioRepository, permisos);
        suspendUser = new SuspendUserHandler(usuarioRepository, permisos);
        activateUser = new ActivateUserHandler(usuarioRepository, permisos);
        approvePendingUser = new ApprovePendingUserHandler(usuarioRepository, permisos);
        getUser = new GetUserHandler(usuarioRepository, permisos);
        getApplicant = new GetApplicantHandler(usuarioRepository, permisos);
        listPendingUsers = new ListPendingUsersHandler(usuarioRepository, permisos);

        registerAnimal = new RegisterAnimalHandler(animalRepository, permisos, clock);
        getAnimal = new GetAnimalHandler(animalRepository, permisos);
        listAnimals = new ListAnimalsHandler(animalRepository, permisos);
        registerBehaviorObservation = new RegisterBehaviorObservationHandler(animalRepository, permisos);
        getAnimalObservations = new GetAnimalObservationsHandler(animalRepository, permisos);
        getAnimalIngressTrace = new GetAnimalIngressTraceHandler(animalRepository, permisos);

        registerClinicalEvaluation = new RegisterClinicalEvaluationHandler(evaluacionRepository, atencion);
        registerVaccination = new RegisterVaccinationHandler(vacunacionRepository, atencion);
        registerDeworming = new RegisterDewormingHandler(desparasitacionRepository, atencion);
        registerTreatment = new RegisterTreatmentHandler(tratamientoRepository, atencion);
        registerSterilization = new RegisterSterilizationHandler(esterilizacionRepository, animalRepository, atencion);
        updateAnimalHealth = new UpdateAnimalHealthHandler(animalRepository, atencion);
        getClinicalHistory = new GetClinicalHistoryHandler(animalRepository, evaluacionRepository,
                vacunacionRepository, desparasitacionRepository, tratamientoRepository,
                esterilizacionRepository, permisos);

        startQuarantine = new StartQuarantineHandler(cuarentenaRepository, animalRepository, cuarentenas, permisos, clock);
        getAnimalsInQuarantine = new GetAnimalsInQuarantineHandler(animalRepository, cuarentenas, permisos);
        checkMinimumQuarantinePeriod = new CheckMinimumQuarantinePeriodHandler(cuarentenas, permisos);
        authorizeQuarantineExit = new AuthorizeQuarantineExitHandler(cuarentenaRepository, cuarentenas, permisos);
        moveAnimalToGeneralPopulation = new MoveAnimalToGeneralPopulationHandler(
                cuarentenaRepository, animalRepository, cuarentenas, permisos);
        getQuarantine = new GetQuarantineHandler(cuarentenas, permisos);
        getAnimalQuarantineStatus = new GetAnimalQuarantineStatusHandler(
                animalRepository, cuarentenaRepository, cuarentenas, permisos);

        getAvailableAnimals = new GetAvailableAnimalsHandler(disponibilidad, permisos);
        submitAdoptionRequest = new SubmitAdoptionRequestHandler(solicitudRepository, adopciones, disponibilidad, permisos);
        getAdoptionRequest = new GetAdoptionRequestHandler(usuarioRepository, adopciones);
        listAdoptionRequests = new ListAdoptionRequestsHandler(solicitudRepository, permisos);
        evaluateBasicCompatibility = new EvaluateBasicCompatibilityHandler(solicitudRepository, adopciones, permisos);
        approveAdoptionRequest = new ApproveAdoptionRequestHandler(
                solicitudRepository, adopciones, disponibilidad, permisos);
        rejectAdoptionRequest = new RejectAdoptionRequestHandler(solicitudRepository, adopciones, permisos);
        formalizeAdoption = new FormalizeAdoptionHandler(adopcionRepository, adopciones, disponibilidad, permisos, clock);
        registerAdoptionFollowUp = new RegisterAdoptionFollowUpHandler(adopcionRepository, adopciones, permisos, clock);
        registerAdoptionReturn = new RegisterAdoptionReturnHandler(adopcionRepository, adopciones, permisos, clock);
        getAdoption = new GetAdoptionHandler(adopciones, permisos);
    }

    /**
     * Carga inicial: registra el primer administrador con la cuenta ya activa.
     * Es necesaria porque toda cuenta nueva nace pendiente y solo un administrador
     * activo puede aprobarla. Es configuración de arranque, no un caso de uso.
     */
    public UsuarioDTO inicializarAdministrador(String nombre, String email) {
        Email correo = Email.of(email);
        if (usuarioRepository.existsByEmail(correo)) {
            throw new EmailAlreadyRegisteredException("Ya existe un usuario registrado con el email " + correo.value());
        }
        Administrador administrador = Administrador.of(
                UsuarioId.generate(), nombre, correo, EstadoCuenta.ACTIVA, LocalDateTime.now());
        return UsuarioDTO.from(usuarioRepository.save(administrador));
    }
}
