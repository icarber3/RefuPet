package co.ufps.refupet.application.cuarentena;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.cuarentena.Cuarentena;
import co.ufps.refupet.domain.cuarentena.ICuarentenaRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Reglas 14 y 15: el veterinario autoriza la salida de cuarentena.
 * La entidad Cuarentena exige rol veterinario y periodo mínimo cumplido.
 */
@ApplicationScoped
public class AuthorizeQuarantineExitHandler {

    private final ICuarentenaRepository cuarentenaRepository;
    private final CuarentenaService cuarentenas;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public AuthorizeQuarantineExitHandler(ICuarentenaRepository cuarentenaRepository, CuarentenaService cuarentenas,
                                          ValidateRolePermissionHandler permisos) {
        this.cuarentenaRepository = cuarentenaRepository;
        this.cuarentenas = cuarentenas;
        this.permisos = permisos;
    }

    @Transactional
    public CuarentenaDTO execute(AuthorizeQuarantineExitCommand command) {
        Usuario veterinario = permisos.execute(
                new ValidateRolePermissionCommand(command.actorId(), Operacion.AUTORIZAR_SALIDA_CUARENTENA));

        Cuarentena cuarentena = cuarentenas.obtener(command.toCuarentenaId());
        cuarentena.autorizarSalida(veterinario, cuarentenas.hoy());
        cuarentenaRepository.save(cuarentena);
        return cuarentenas.toDTO(cuarentena);
    }
}
