package com.hotel.command;

import com.hotel.core.GestorReservas;
import com.hotel.models.Reserva;
import com.hotel.strategy.PagoStrategy;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.PagoNoProcesadoException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public class ConfirmarReservaCommand implements Command {
    private GestorReservas gestor;
    private String idReserva;
    private PagoStrategy estrategiaPago;

    public ConfirmarReservaCommand(GestorReservas gestor, String idReserva, PagoStrategy estrategiaPago) {
        this.gestor = gestor;
        this.idReserva = idReserva;
        this.estrategiaPago = estrategiaPago;
    }

    @Override
    public void execute() throws ConfirmacionReservaException, PagoNoProcesadoException, EstadoReservaInvalidoException {
        Reserva reserva = gestor.obtenerReserva(idReserva)
                                .orElseThrow(() -> new ConfirmacionReservaException("Reserva no encontrada con ID: " + idReserva));

        if (reserva.procesarPago(estrategiaPago)) {
            gestor.confirmarReserva(idReserva);
            System.out.println("Comando: Reserva " + idReserva + " confirmada y pago procesado.");
        } else {
            throw new PagoNoProcesadoException("El pago para la reserva " + idReserva + " no pudo ser procesado.");
        }
    }
}
