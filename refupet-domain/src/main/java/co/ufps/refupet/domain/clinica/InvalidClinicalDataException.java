package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.DomainException;

/**
 * Datos clinicos incoherentes en un registro veterinario.
 */
public class InvalidClinicalDataException extends DomainException {
    public InvalidClinicalDataException(String message) {
        super(message);
    }
}
