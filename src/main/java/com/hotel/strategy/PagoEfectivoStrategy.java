package com.hotel.strategy;

import com.hotel.exceptions.PagoNoProcesadoException;

public class PagoEfectivoStrategy implements PagoStrategy {
    @Override
    public boolean procesarPago(double monto) throws PagoNoProcesadoException {
        System.out.println("Procesando pago en Efectivo por " + String.format("%.2f", monto) + "...");
        // Simulación de lógica de pago
        if (monto > 0) {
            System.out.println("Pago en efectivo procesado exitosamente.");
            return true;
        } else {
            throw new PagoNoProcesadoException("Monto inválido para pago en efectivo.");
        }
    }
}
