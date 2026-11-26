package co.ufps.refupet.domain.clinica;

import co.ufps.refupet.domain.Validaciones;
import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.usuario.UsuarioId;

import java.time.LocalDate;

/**
 * Desparasitación aplicada a un animal (regla 7).
 * Registro propio: se repite periódicamente y cada aplicación forma parte del historial.
 */
public class Desparasitacion extends RegistroClinico {

    private final DesparasitacionId desparasitacionId;
    private final String producto;

    private Desparasitacion(DesparasitacionId id, AnimalId animalId, UsuarioId veterinarioId,
                            LocalDate fecha, String producto, String observaciones) {
        super(animalId, veterinarioId, fecha, observaciones);
        this.desparasitacionId = Validaciones.obligatorio(id, "desparasitacionId");
        this.producto = Validaciones.textoObligatorio(producto, "producto");
    }

    public static Desparasitacion create(AnimalId animalId, UsuarioId veterinarioId, LocalDate fecha,
                                         String producto, String observaciones) {
        return new Desparasitacion(DesparasitacionId.generate(), animalId, veterinarioId, fecha, producto, observaciones);
    }

    public static Desparasitacion of(DesparasitacionId id, AnimalId animalId, UsuarioId veterinarioId,
                                     LocalDate fecha, String producto, String observaciones) {
        return new Desparasitacion(id, animalId, veterinarioId, fecha, producto, observaciones);
    }

    @Override
    public DesparasitacionId getId() {
        return desparasitacionId;
    }

    @Override
    public TipoRegistroClinico getTipo() {
        return TipoRegistroClinico.DESPARASITACION;
    }

    @Override
    public String resumen() {
        return "Desparasitación: " + producto;
    }

    public String getProducto() { return producto; }
}
