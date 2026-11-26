package co.ufps.refupet.domain.animal;

import co.ufps.refupet.domain.ResourceNotFoundException;


/**
 * No existe Animal con el identificador indicado.
 */
public class AnimalNotFoundException extends ResourceNotFoundException {
    public AnimalNotFoundException(AnimalId id) {
        super("Animal", id.value());
    }
}
