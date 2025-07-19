package com.hotel.state;

import com.hotel.models.Reserva;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public class ReservaCancelada implements ReservaState {
    @Override
    public void confirmar(Reserva reserva) throws ConfirmacionReservaException, EstadoReservaInvalidoException {
        throw new ConfirmacionReservaException("Una reserva cancelada no puede ser confirmada.");
    }

    @Override
    public void cancelar(Reserva reserva) throws EstadoReservaInvalidoException {
        throw new EstadoReservaInvalidoException("La reserva " + reserva.getId() + " ya está cancelada.");
    }

    @Override
    public void pendificar(Reserva reserva) throws EstadoReservaInvalidoException {
        // En algunos casos, se podría permitir reactivarla a pendiente, pero por ahora no
        throw new EstadoReservaInvalidoException("Una reserva cancelada no puede volver a estado Pendiente.");
    }

    @Override
    public String obtenerNombreEstado() {
        return "Cancelada";
    }
}
