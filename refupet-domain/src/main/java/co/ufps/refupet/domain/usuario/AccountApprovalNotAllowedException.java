package co.ufps.refupet.domain.usuario;

/**
 * Se intento aprobar una cuenta que no esta pendiente de aprobacion.
 */
public class AccountApprovalNotAllowedException extends InvalidUserStateException {
    public AccountApprovalNotAllowedException(String message) {
        super(message);
    }
}
