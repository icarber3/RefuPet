package co.ufps.refupet.application.cuarentena;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.Operacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Regla 14: controlar el cumplimiento del periodo mínimo. El DTO informa si se cumplió,
 * la fecha mínima de salida y los días restantes a la fecha actual.
 */
@ApplicationScoped
public class CheckMinimumQuarantinePeriodHandler {

    private final CuarentenaService cuarentenas;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public CheckMinimumQuarantinePeriodHandler(CuarentenaService cuarentenas, ValidateRolePermissionHandler permisos) {
        this.cuarentenas = cuarentenas;
        this.permisos = permisos;
    }

    public CuarentenaDTO execute(CheckMinimumQuarantinePeriodQuery query) {
        permisos.execute(new ValidateRolePermissionCommand(query.actorId(), Operacion.CONSULTAR_CUARENTENAS));
        return cuarentenas.toDTO(cuarentenas.obtener(query.toCuarentenaId()));
    }
}
