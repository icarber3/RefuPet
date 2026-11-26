package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.animal.AnimalId;

import java.util.List;

/**
 * Puerto de persistencia de desparasitaciones.
 * Solo expone lo que necesitan los casos de uso: registrar y consultar el historial de un animal.
 */
public interface IDesparasitacionRepository {
    Desparasitacion save(Desparasitacion registro);

    List<Desparasitacion> findByAnimalId(AnimalId animalId);
}
