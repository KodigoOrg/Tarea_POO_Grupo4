package com.hotel.state;

import com.hotel.models.Reserva;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public class ReservaPendiente implements ReservaState {
    @Override
    public void confirmar(Reserva reserva) throws ConfirmacionReservaException, EstadoReservaInvalidoException {
        // Lógica para confirmar (ej. verificar pago)
        System.out.println("Cambiando estado de Reserva " + reserva.getId() + ": Pendiente -> Confirmada.");
        reserva.setEstadoActual(new ReservaConfirmada());
    }

    @Override
    public void cancelar(Reserva reserva) throws EstadoReservaInvalidoException {
        System.out.println("Cambiando estado de Reserva " + reserva.getId() + ": Pendiente -> Cancelada.");
        reserva.setEstadoActual(new ReservaCancelada());
        reserva.getHabitacion().setDisponible(true); // Liberar habitación
    }

    @Override
    public void pendificar(Reserva reserva) throws EstadoReservaInvalidoException {
        throw new EstadoReservaInvalidoException("La reserva ya está en estado Pendiente.");
    }

    @Override
    public String obtenerNombreEstado() {
        return "Pendiente";
    }
}
