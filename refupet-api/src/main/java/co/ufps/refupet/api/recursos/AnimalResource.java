package co.ufps.refupet.api.recursos;

import co.ufps.refupet.api.dto.ActualizarEstadoSaludRequest;
import co.ufps.refupet.api.dto.IniciarCuarentenaRequest;
import co.ufps.refupet.api.dto.RegistrarAnimalRequest;
import co.ufps.refupet.api.dto.RegistrarDesparasitacionRequest;
import co.ufps.refupet.api.dto.RegistrarEsterilizacionRequest;
import co.ufps.refupet.api.dto.RegistrarEvaluacionClinicaRequest;
import co.ufps.refupet.api.dto.RegistrarObservacionRequest;
import co.ufps.refupet.api.dto.RegistrarTratamientoRequest;
import co.ufps.refupet.api.dto.RegistrarVacunacionRequest;
import co.ufps.refupet.application.adopcion.GetAvailableAnimalsHandler;
import co.ufps.refupet.application.adopcion.GetAvailableAnimalsQuery;
import co.ufps.refupet.application.animal.AnimalDTO;
import co.ufps.refupet.application.animal.GetAnimalHandler;
import co.ufps.refupet.application.animal.GetAnimalIngressTraceHandler;
import co.ufps.refupet.application.animal.GetAnimalIngressTraceQuery;
import co.ufps.refupet.application.animal.GetAnimalObservationsHandler;
import co.ufps.refupet.application.animal.GetAnimalObservationsQuery;
import co.ufps.refupet.application.animal.GetAnimalQuery;
import co.ufps.refupet.application.animal.ListAnimalsHandler;
import co.ufps.refupet.application.animal.ListAnimalsQuery;
import co.ufps.refupet.application.animal.ObservacionComportamientoDTO;
import co.ufps.refupet.application.animal.RegisterAnimalHandler;
import co.ufps.refupet.application.animal.RegisterBehaviorObservationHandler;
import co.ufps.refupet.application.animal.RegistroIngresoDTO;
import co.ufps.refupet.application.clinica.GetClinicalHistoryHandler;
import co.ufps.refupet.application.clinica.GetClinicalHistoryQuery;
import co.ufps.refupet.application.clinica.HistorialClinicoDTO;
import co.ufps.refupet.application.clinica.RegisterClinicalEvaluationHandler;
import co.ufps.refupet.application.clinica.RegisterDewormingHandler;
import co.ufps.refupet.application.clinica.RegisterSterilizationHandler;
import co.ufps.refupet.application.clinica.RegisterTreatmentHandler;
import co.ufps.refupet.application.clinica.RegisterVaccinationHandler;
import co.ufps.refupet.application.clinica.UpdateAnimalHealthHandler;
import co.ufps.refupet.application.cuarentena.CuarentenaDTO;
import co.ufps.refupet.application.cuarentena.GetAnimalQuarantineStatusHandler;
import co.ufps.refupet.application.cuarentena.GetAnimalQuarantineStatusQuery;
import co.ufps.refupet.application.cuarentena.GetAnimalsInQuarantineHandler;
import co.ufps.refupet.application.cuarentena.GetAnimalsInQuarantineQuery;
import co.ufps.refupet.application.cuarentena.StartQuarantineHandler;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

import static co.ufps.refupet.api.Peticiones.CABECERA_ACTOR;
import static co.ufps.refupet.api.Peticiones.actor;
import static co.ufps.refupet.api.Peticiones.cuerpo;

/**
 * Animales del refugio y las operaciones que cuelgan de un animal concreto: ingreso y observaciones
 * (reglas 1-5), atención veterinaria (reglas 6-11), inicio y estado de cuarentena (reglas 12-14)
 * y disponibilidad para adopción (regla 17). La gestión de una cuarentena ya iniciada está en
 * {@link CuarentenaResource}.
 */
@Path("/api/animales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnimalResource {

    @Inject RegisterAnimalHandler registerAnimal;
    @Inject ListAnimalsHandler listAnimals;
    @Inject GetAnimalHandler getAnimal;
    @Inject GetAnimalIngressTraceHandler getAnimalIngressTrace;
    @Inject GetAnimalObservationsHandler getAnimalObservations;
    @Inject RegisterBehaviorObservationHandler registerBehaviorObservation;
    @Inject GetAvailableAnimalsHandler getAvailableAnimals;
    @Inject UpdateAnimalHealthHandler updateAnimalHealth;
    @Inject RegisterClinicalEvaluationHandler registerClinicalEvaluation;
    @Inject RegisterVaccinationHandler registerVaccination;
    @Inject RegisterDewormingHandler registerDeworming;
    @Inject RegisterTreatmentHandler registerTreatment;
    @Inject RegisterSterilizationHandler registerSterilization;
    @Inject GetClinicalHistoryHandler getClinicalHistory;
    @Inject StartQuarantineHandler startQuarantine;
    @Inject GetAnimalQuarantineStatusHandler getAnimalQuarantineStatus;
    @Inject GetAnimalsInQuarantineHandler getAnimalsInQuarantine;

    // ── Animales ──

    /** Registro del ingreso de un animal (reglas 1 y 35). 201 + Location. */
    @POST
    public Response registrar(@HeaderParam(CABECERA_ACTOR) String actorId, RegistrarAnimalRequest peticion) {
        AnimalDTO animal = registerAnimal.execute(cuerpo(peticion).toCommand(actor(actorId)));
        return Response.created(URI.create("/api/animales/" + animal.animalId())).entity(animal).build();
    }

    @GET
    public List<AnimalDTO> listar(@HeaderParam(CABECERA_ACTOR) String actorId) {
        return listAnimals.execute(new ListAnimalsQuery(actor(actorId)));
    }

    /** Animales disponibles para adopción (reglas 17 y 37). */
    @GET
    @Path("/disponibles")
    public List<AnimalDTO> disponibles(@HeaderParam(CABECERA_ACTOR) String actorId) {
        return getAvailableAnimals.execute(new GetAvailableAnimalsQuery(actor(actorId)));
    }

    /** Cuarentenas activas, con el animal correspondiente (regla 13). */
    @GET
    @Path("/en-cuarentena")
    public List<CuarentenaDTO> enCuarentena(@HeaderParam(CABECERA_ACTOR) String actorId) {
        return getAnimalsInQuarantine.execute(new GetAnimalsInQuarantineQuery(actor(actorId)));
    }

    @GET
    @Path("/{animalId}")
    public AnimalDTO obtener(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId) {
        return getAnimal.execute(new GetAnimalQuery(actor(actorId), animalId));
    }

    /** Trazabilidad del ingreso (regla 5). */
    @GET
    @Path("/{animalId}/ingreso")
    public RegistroIngresoDTO ingreso(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId) {
        return getAnimalIngressTrace.execute(new GetAnimalIngressTraceQuery(actor(actorId), animalId));
    }

    @GET
    @Path("/{animalId}/observaciones")
    public List<ObservacionComportamientoDTO> observaciones(@HeaderParam(CABECERA_ACTOR) String actorId,
                                                            @PathParam("animalId") String animalId) {
        return getAnimalObservations.execute(new GetAnimalObservationsQuery(actor(actorId), animalId));
    }

    /** Observación de comportamiento (reglas 4 y 36). 201. */
    @POST
    @Path("/{animalId}/observaciones")
    public Response registrarObservacion(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                         RegistrarObservacionRequest peticion) {
        return creado(registerBehaviorObservation.execute(cuerpo(peticion).toCommand(actor(actorId), animalId)));
    }

    // ── Atención veterinaria ──

    /** Actualiza el estado de salud vigente (regla 11). */
    @PUT
    @Path("/{animalId}/estado-salud")
    public AnimalDTO actualizarEstadoSalud(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                           ActualizarEstadoSaludRequest peticion) {
        return updateAnimalHealth.execute(cuerpo(peticion).toCommand(actor(actorId), animalId));
    }

    @POST
    @Path("/{animalId}/evaluaciones-clinicas")
    public Response registrarEvaluacionClinica(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                               RegistrarEvaluacionClinicaRequest peticion) {
        return creado(registerClinicalEvaluation.execute(cuerpo(peticion).toCommand(actor(actorId), animalId)));
    }

    @POST
    @Path("/{animalId}/vacunaciones")
    public Response registrarVacunacion(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                        RegistrarVacunacionRequest peticion) {
        return creado(registerVaccination.execute(cuerpo(peticion).toCommand(actor(actorId), animalId)));
    }

    @POST
    @Path("/{animalId}/desparasitaciones")
    public Response registrarDesparasitacion(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                             RegistrarDesparasitacionRequest peticion) {
        return creado(registerDeworming.execute(cuerpo(peticion).toCommand(actor(actorId), animalId)));
    }

    @POST
    @Path("/{animalId}/tratamientos")
    public Response registrarTratamiento(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                         RegistrarTratamientoRequest peticion) {
        return creado(registerTreatment.execute(cuerpo(peticion).toCommand(actor(actorId), animalId)));
    }

    /** Esterilización (regla 9): una sola por animal. El cuerpo es opcional. */
    @POST
    @Path("/{animalId}/esterilizacion")
    public Response registrarEsterilizacion(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                            RegistrarEsterilizacionRequest peticion) {
        return creado(registerSterilization.execute(
                RegistrarEsterilizacionRequest.toCommand(peticion, actor(actorId), animalId)));
    }

    /** Historial clínico ordenado por fecha (regla 10). */
    @GET
    @Path("/{animalId}/historial-clinico")
    public HistorialClinicoDTO historialClinico(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId) {
        return getClinicalHistory.execute(new GetClinicalHistoryQuery(actor(actorId), animalId));
    }

    // ── Cuarentena del animal ──

    /** Inicio de cuarentena (regla 12). 201 + Location de la cuarentena creada. */
    @POST
    @Path("/{animalId}/cuarentenas")
    public Response iniciarCuarentena(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId,
                                      IniciarCuarentenaRequest peticion) {
        CuarentenaDTO cuarentena = startQuarantine.execute(cuerpo(peticion).toCommand(actor(actorId), animalId));
        return Response.created(URI.create("/api/cuarentenas/" + cuarentena.cuarentenaId())).entity(cuarentena).build();
    }

    /** Cuarentena activa o, si no hay, la más reciente. 204 si el animal nunca estuvo en cuarentena. */
    @GET
    @Path("/{animalId}/cuarentena")
    public Response estadoCuarentena(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("animalId") String animalId) {
        return getAnimalQuarantineStatus.execute(new GetAnimalQuarantineStatusQuery(actor(actorId), animalId))
                .map(dto -> Response.ok(dto).build())
                .orElseGet(() -> Response.noContent().build());
    }

    private static Response creado(Object entidad) {
        return Response.status(Response.Status.CREATED).entity(entidad).build();
    }
}
