package co.ufps.refupet.domain.usuario;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia de usuarios (todas las subclases de Usuario).
 */
public interface IUsuarioRepository {
    Usuario save(Usuario usuario);

    Optional<Usuario> findById(UsuarioId usuarioId);

    Optional<Usuario> findByEmail(Email email);

    boolean existsByEmail(Email email);

    List<Usuario> findByEstadoCuenta(EstadoCuenta estadoCuenta);
}
