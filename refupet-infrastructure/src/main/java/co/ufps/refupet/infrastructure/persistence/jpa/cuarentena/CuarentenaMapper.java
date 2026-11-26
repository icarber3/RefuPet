package co.ufps.refupet.infrastructure.persistence.jpa.cuarentena;

import co.ufps.refupet.domain.animal.AnimalId;
import co.ufps.refupet.domain.cuarentena.Cuarentena;
import co.ufps.refupet.domain.cuarentena.CuarentenaId;
import co.ufps.refupet.domain.cuarentena.PeriodoCuarentena;
import co.ufps.refupet.domain.usuario.UsuarioId;

import java.util.Objects;

/** Data Mapper del agregado Cuarentena. Reconstruye con {@code Cuarentena.of} y {@code PeriodoCuarentena}. */
public final class CuarentenaMapper {

    private CuarentenaMapper() {
    }

    public static CuarentenaJpa toJpa(Cuarentena cuarentena) {
        Objects.requireNonNull(cuarentena, "cuarentena");
        CuarentenaJpa jpa = new CuarentenaJpa();
        jpa.setId(cuarentena.getCuarentenaId().value());
        jpa.setAnimalId(cuarentena.getAnimalId().value());
        jpa.setFechaInicio(cuarentena.getPeriodo().fechaInicio());
        jpa.setDiasMinimos(cuarentena.getPeriodo().diasMinimos());
        jpa.setIniciadaPor(cuarentena.getIniciadaPor().value());
        jpa.setEstado(cuarentena.getEstado());
        jpa.setSalidaAutorizadaPor(cuarentena.getSalidaAutorizadaPor() == null
                ? null : cuarentena.getSalidaAutorizadaPor().value());
        jpa.setFechaAutorizacionSalida(cuarentena.getFechaAutorizacionSalida());
        jpa.setFechaFinalizacion(cuarentena.getFechaFinalizacion());
        return jpa;
    }

    public static Cuarentena toDomain(CuarentenaJpa jpa) {
        Objects.requireNonNull(jpa, "cuarentenaJpa");
        return Cuarentena.of(
                CuarentenaId.of(jpa.getId()),
                AnimalId.of(jpa.getAnimalId()),
                new PeriodoCuarentena(jpa.getFechaInicio(), jpa.getDiasMinimos()),
                UsuarioId.of(jpa.getIniciadaPor()),
                jpa.getEstado(),
                jpa.getSalidaAutorizadaPor() == null ? null : UsuarioId.of(jpa.getSalidaAutorizadaPor()),
                jpa.getFechaAutorizacionSalida(),
                jpa.getFechaFinalizacion());
    }
}
