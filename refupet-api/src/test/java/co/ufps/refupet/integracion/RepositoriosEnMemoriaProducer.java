package co.ufps.refupet.integracion;

import co.ufps.refupet.domain.adopcion.IAdopcionRepository;
import co.ufps.refupet.domain.adopcion.ISolicitudAdopcionRepository;
import co.ufps.refupet.domain.animal.Animal;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.animal.IAnimalRepository;
import co.ufps.refupet.domain.clinica.Esterilizacion;
import co.ufps.refupet.domain.clinica.IDesparasitacionRepository;
import co.ufps.refupet.domain.clinica.IEsterilizacionRepository;
import co.ufps.refupet.domain.clinica.IEvaluacionClinicaRepository;
import co.ufps.refupet.domain.clinica.ITratamientoRepository;
import co.ufps.refupet.domain.clinica.IVacunacionRepository;
import co.ufps.refupet.domain.cuarentena.Cuarentena;
import co.ufps.refupet.domain.cuarentena.ICuarentenaRepository;
import co.ufps.refupet.domain.usuario.IUsuarioRepository;
import co.ufps.refupet.domain.usuario.Usuario;
import co.ufps.refupet.domain.usuario.UsuarioId;
import co.ufps.refupet.infrastructure.persistence.memory.AdopcionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.AnimalRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.CuarentenaRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.DesparasitacionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.EsterilizacionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.EvaluacionClinicaRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.SolicitudAdopcionRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.TratamientoRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.UsuarioRepositoryEnMemoria;
import co.ufps.refupet.infrastructure.persistence.memory.VacunacionRepositoryEnMemoria;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

/**
 * Alternativa de PRUEBA: sustituye los adaptadores JPA por los repositorios en memoria existentes.
 *
 * Es una {@code @Alternative} sin prioridad, así que está DESACTIVADA por defecto y solo se selecciona
 * en los perfiles de prueba que la habilitan explícitamente ({@code getEnabledAlternatives}). Vive en
 * src/test: nunca forma parte del artefacto de producción.
 *
 * Usuario, Animal, Cuarentena y Esterilizacion se instrumentan con {@link RegistroTransacciones} para
 * observar la transacción activa en cada operación (sin tocar la base de datos).
 */
@Alternative
@Singleton
public class RepositoriosEnMemoriaProducer {

    @Inject
    RegistroTransacciones registro;

    @Produces @Alternative @Singleton
    IUsuarioRepository usuarios() {
        return new UsuarioRepositoryEnMemoria() {
            @Override
            public Optional<Usuario> findById(UsuarioId id) {
                registro.anotar("usuario.findById");
                return super.findById(id);
            }

            @Override
            public Usuario save(Usuario usuario) {
                registro.anotar("usuario.save");
                return super.save(usuario);
            }
        };
    }

    @Produces @Alternative @Singleton
    IAnimalRepository animales() {
        return new AnimalRepositoryEnMemoria() {
            @Override
            public Optional<Animal> findById(AnimalId id) {
                registro.anotar("animal.findById");
                return super.findById(id);
            }

            @Override
            public Animal save(Animal animal) {
                registro.anotar("animal.save");
                return super.save(animal);
            }
        };
    }

    @Produces @Alternative @Singleton
    ICuarentenaRepository cuarentenas() {
        return new CuarentenaRepositoryEnMemoria() {
            @Override
            public Cuarentena save(Cuarentena cuarentena) {
                registro.anotar("cuarentena.save");
                return super.save(cuarentena);
            }
        };
    }

    @Produces @Alternative @Singleton
    IEsterilizacionRepository esterilizaciones() {
        return new EsterilizacionRepositoryEnMemoria() {
            @Override
            public Esterilizacion save(Esterilizacion registroClinico) {
                registro.anotar("esterilizacion.save");
                return super.save(registroClinico);
            }
        };
    }

    @Produces @Alternative @Singleton
    IEvaluacionClinicaRepository evaluaciones() {
        return new EvaluacionClinicaRepositoryEnMemoria();
    }

    @Produces @Alternative @Singleton
    IVacunacionRepository vacunaciones() {
        return new VacunacionRepositoryEnMemoria();
    }

    @Produces @Alternative @Singleton
    IDesparasitacionRepository desparasitaciones() {
        return new DesparasitacionRepositoryEnMemoria();
    }

    @Produces @Alternative @Singleton
    ITratamientoRepository tratamientos() {
        return new TratamientoRepositoryEnMemoria();
    }

    @Produces @Alternative @Singleton
    ISolicitudAdopcionRepository solicitudes() {
        return new SolicitudAdopcionRepositoryEnMemoria();
    }

    @Produces @Alternative @Singleton
    IAdopcionRepository adopciones() {
        return new AdopcionRepositoryEnMemoria();
    }
}
