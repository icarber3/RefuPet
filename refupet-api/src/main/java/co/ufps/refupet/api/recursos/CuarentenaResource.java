package co.ufps.refupet.api.recursos;

import co.ufps.refupet.application.animal.AnimalDTO;
import co.ufps.refupet.application.cuarentena.AuthorizeQuarantineExitCommand;
import co.ufps.refupet.application.cuarentena.AuthorizeQuarantineExitHandler;
import co.ufps.refupet.application.cuarentena.CheckMinimumQuarantinePeriodHandler;
import co.ufps.refupet.application.cuarentena.CheckMinimumQuarantinePeriodQuery;
import co.ufps.refupet.application.cuarentena.CuarentenaDTO;
import co.ufps.refupet.application.cuarentena.GetQuarantineHandler;
import co.ufps.refupet.application.cuarentena.GetQuarantineQuery;
import co.ufps.refupet.application.cuarentena.MoveAnimalToGeneralPopulationCommand;
import co.ufps.refupet.application.cuarentena.MoveAnimalToGeneralPopulationHandler;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import static co.ufps.refupet.api.Peticiones.CABECERA_ACTOR;
import static co.ufps.refupet.api.Peticiones.actor;

/** Gestión de una cuarentena ya iniciada (reglas 13 a 16). El inicio está en {@link AnimalResource}. */
@Path("/api/cuarentenas")
@Produces(MediaType.APPLICATION_JSON)
public class CuarentenaResource {

    @Inject GetQuarantineHandler getQuarantine;
    @Inject CheckMinimumQuarantinePeriodHandler checkMinimumQuarantinePeriod;
    @Inject AuthorizeQuarantineExitHandler authorizeQuarantineExit;
    @Inject MoveAnimalToGeneralPopulationHandler moveAnimalToGeneralPopulation;

    @GET
    @Path("/{cuarentenaId}")
    public CuarentenaDTO obtener(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("cuarentenaId") String cuarentenaId) {
        return getQuarantine.execute(new GetQuarantineQuery(actor(actorId), cuarentenaId));
    }

    /** Control del periodo mínimo (regla 14): fecha mínima de salida, días restantes y si se cumplió. */
    @GET
    @Path("/{cuarentenaId}/periodo-minimo")
    public CuarentenaDTO periodoMinimo(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("cuarentenaId") String cuarentenaId) {
        return checkMinimumQuarantinePeriod.execute(new CheckMinimumQuarantinePeriodQuery(actor(actorId), cuarentenaId));
    }

    /** Autorización de salida por un veterinario, con el periodo mínimo cumplido (reglas 14 y 15). */
    @POST
    @Path("/{cuarentenaId}/autorizacion-salida")
    public CuarentenaDTO autorizarSalida(@HeaderParam(CABECERA_ACTOR) String actorId, @PathParam("cuarentenaId") String cuarentenaId) {
        return authorizeQuarantineExit.execute(new AuthorizeQuarantineExitCommand(actor(actorId), cuarentenaId));
    }

    /** Paso a población general; finaliza la cuarentena (regla 16). */
    @POST
    @Path("/{cuarentenaId}/traslado-poblacion-general")
    public AnimalDTO trasladarAPoblacionGeneral(@HeaderParam(CABECERA_ACTOR) String actorId,
                                                @PathParam("cuarentenaId") String cuarentenaId) {
        return moveAnimalToGeneralPopulation.execute(new MoveAnimalToGeneralPopulationCommand(actor(actorId), cuarentenaId));
    }
}
