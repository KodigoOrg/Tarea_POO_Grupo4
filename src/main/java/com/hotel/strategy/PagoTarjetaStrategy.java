package com.hotel.strategy;

import com.hotel.exceptions.PagoNoProcesadoException;

public class PagoTarjetaStrategy implements PagoStrategy {
    @Override
    public boolean procesarPago(double monto) throws PagoNoProcesadoException {
        System.out.println("Procesando pago con Tarjeta de Crédito por " + String.format("%.2f", monto) + "...");
        // Simulación de lógica de pago
        if (monto > 0) {
            System.out.println("Pago con tarjeta procesado exitosamente.");
            return true;
        } else {
            throw new PagoNoProcesadoException("Monto inválido para pago con tarjeta.");
        }
    }
}
