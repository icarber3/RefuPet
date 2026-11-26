package co.ufps.refupet.application.usuario;

import co.ufps.refupet.application.seguridad.ValidateRolePermissionCommand;
import co.ufps.refupet.application.seguridad.ValidateRolePermissionHandler;
import co.ufps.refupet.domain.usuario.Email;
import co.ufps.refupet.domain.usuario.EmailAlreadyRegisteredException;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Operacion;
import co.ufps.refupet.domain.usuario.Solicitante;
import co.ufps.refupet.domain.usuario.Usuario;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.domain.usuario.UsuarioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Regla 27: el administrador modifica la información de un usuario.
 * El email debe seguir siendo único entre todos los usuarios.
 */
@ApplicationScoped
public class UpdateUserHandler {

    private final IUsuarioRepository usuarioRepository;
    private final ValidateRolePermissionHandler permisos;

    @Inject
    public UpdateUserHandler(IUsuarioRepository usuarioRepository, ValidateRolePermissionHandler permisos) {
        this.usuarioRepository = usuarioRepository;
        this.permisos = permisos;
    }

    @Transactional
    public UsuarioDTO execute(UpdateUserCommand command) {
        permisos.execute(new ValidateRolePermissionCommand(command.actorId(), Operacion.GESTIONAR_USUARIOS));

        UsuarioId usuarioId = command.toUsuarioId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException(usuarioId));

        Email nuevoEmail = command.toEmail();
        usuarioRepository.findByEmail(nuevoEmail)
                .filter(otro -> !otro.getUsuarioId().equals(usuarioId))
                .ifPresent(otro -> {
                    throw new EmailAlreadyRegisteredException(
                            "El email " + nuevoEmail.value() + " ya pertenece a otro usuario");
                });

        usuario.actualizarDatos(command.nombre(), nuevoEmail);
        if (usuario instanceof Solicitante solicitante
                && (command.telefono() != null || command.direccion() != null)) {
            solicitante.actualizarContacto(command.telefono(), command.direccion());
        }
        return UsuarioDTO.from(usuarioRepository.save(usuario));
    }
}
