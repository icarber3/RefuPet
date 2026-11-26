package co.ufps.refupet.domain.usuario;

/**
 * Estado de la cuenta de un usuario.
 * Transiciones válidas (encapsuladas en Usuario):
 * PENDIENTE_APROBACION -> ACTIVA (aprobar), ACTIVA -> SUSPENDIDA (suspender),
 * SUSPENDIDA -> ACTIVA (activar).
 */
public enum EstadoCuenta {
    PENDIENTE_APROBACION,
    ACTIVA,
    SUSPENDIDA
}
