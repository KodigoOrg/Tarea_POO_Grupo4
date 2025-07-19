package com.hotel.state;

import com.hotel.models.Reserva;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public interface ReservaState {
    void confirmar(Reserva reserva) throws ConfirmacionReservaException, EstadoReservaInvalidoException;
    void cancelar(Reserva reserva) throws EstadoReservaInvalidoException;
    void pendificar(Reserva reserva) throws EstadoReservaInvalidoException;
    String obtenerNombreEstado();
}
