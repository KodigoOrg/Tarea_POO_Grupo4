package com.hotel.command;

import com.hotel.core.GestorReservas;
import com.hotel.exceptions.EstadoReservaInvalidoException;

public class CancelarReservaCommand implements Command {
    private GestorReservas gestor;
    private String idReserva;

    public CancelarReservaCommand(GestorReservas gestor, String idReserva) {
        this.gestor = gestor;
        this.idReserva = idReserva;
    }

    @Override
    public void execute() throws EstadoReservaInvalidoException {
        gestor.cancelarReserva(idReserva);
        System.out.println("Comando: Reserva " + idReserva + " cancelada.");
    }
}
