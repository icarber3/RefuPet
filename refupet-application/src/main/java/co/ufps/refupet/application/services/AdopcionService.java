package co.ufps.refupet.application.services;

import co.ufps.refupet.domain.adopcion.Adopcion;
import co.ufps.refupet.domain.adopcion.AdopcionId;
import co.ufps.refupet.domain.adopcion.AdopcionNotFoundException;
import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcion;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcionId;
import co.ufps.refupet.domain.adopcion.SolicitudAdopcionNotFoundException;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.AnimalNotFoundException;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Búsquedas comunes del módulo de adopción, con la excepción de dominio correspondiente
 * cuando el recurso no existe.
 */
@ApplicationScoped
public class AdopcionService {

    private final ISolicitudAdopcionRepository solicitudRepository;
    private final IAdopcionRepository adopcionRepository;
    private final IAnimalRepository animalRepository;

    @Inject
    public AdopcionService(ISolicitudAdopcionRepository solicitudRepository, IAdopcionRepository adopcionRepository,
                           IAnimalRepository animalRepository) {
        this.solicitudRepository = solicitudRepository;
        this.adopcionRepository = adopcionRepository;
        this.animalRepository = animalRepository;
    }

    public SolicitudAdopcion obtenerSolicitud(SolicitudAdopcionId solicitudId) {
        return solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new SolicitudAdopcionNotFoundException(solicitudId));
    }

    public Adopcion obtenerAdopcion(AdopcionId adopcionId) {
        return adopcionRepository.findById(adopcionId)
                .orElseThrow(() -> new AdopcionNotFoundException(adopcionId));
    }

    public Animal obtenerAnimal(AnimalId animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
    }
}
