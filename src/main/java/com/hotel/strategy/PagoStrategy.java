package com.hotel.strategy;

import com.hotel.exceptions.PagoNoProcesadoException;

public interface PagoStrategy {
    boolean procesarPago(double monto) throws PagoNoProcesadoException;
}
