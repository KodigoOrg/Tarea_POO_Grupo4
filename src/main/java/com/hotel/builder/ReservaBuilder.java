package com.hotel.builder;

import com.hotel.models.Cliente;
import com.hotel.models.Habitacion;
import com.hotel.models.Reserva;
import com.hotel.interfaces.Servicio;
import com.hotel.exceptions.ReservaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaBuilder {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;
    private List<Servicio> serviciosAdicionales = new ArrayList<>();

    public ReservaBuilder conId(String id) {
        this.id = id;
        return this;
    }

    public ReservaBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder conHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
        return this;
    }

    public ReservaBuilder conFechas(LocalDate fechaLlegada, LocalDate fechaSalida) {
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        return this;
    }

    public ReservaBuilder conServicio(Servicio servicio) {
        this.serviciosAdicionales.add(servicio);
        return this;
    }

    public Reserva build() throws ReservaInvalidaException {
        if (id == null || id.trim().isEmpty()) {
            throw new ReservaInvalidaException("El ID de la reserva es obligatorio.");
        }
        if (cliente == null) {
            throw new ReservaInvalidaException("El cliente de la reserva es obligatorio.");
        }
        if (habitacion == null) {
            throw new ReservaInvalidaException("La habitación de la reserva es obligatoria.");
        }
        if (fechaLlegada == null || fechaSalida == null || fechaLlegada.isAfter(fechaSalida)) {
            throw new ReservaInvalidaException("Las fechas de llegada y salida son inválidas.");
        }

        return new Reserva(id, cliente, habitacion, fechaLlegada, fechaSalida, serviciosAdicionales);
    }
}
