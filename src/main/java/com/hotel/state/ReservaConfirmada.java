package com.hotel.state;

import com.hotel.models.Reserva;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public class ReservaConfirmada implements ReservaState {
    @Override
    public void confirmar(Reserva reserva) throws ConfirmacionReservaException, EstadoReservaInvalidoException {
        throw new ConfirmacionReservaException("La reserva " + reserva.getId() + " ya está confirmada.");
    }

    @Override
    public void cancelar(Reserva reserva) throws EstadoReservaInvalidoException {
        System.out.println("Cambiando estado de Reserva " + reserva.getId() + ": Confirmada -> Cancelada.");
        reserva.setEstadoActual(new ReservaCancelada());
        reserva.getHabitacion().setDisponible(true); // Liberar habitación
    }

    @Override
    public void pendificar(Reserva reserva) throws EstadoReservaInvalidoException {
        throw new EstadoReservaInvalidoException("No se puede pasar de Confirmada a Pendiente directamente.");
    }

    @Override
    public String obtenerNombreEstado() {
        return "Confirmada";
    }
}
