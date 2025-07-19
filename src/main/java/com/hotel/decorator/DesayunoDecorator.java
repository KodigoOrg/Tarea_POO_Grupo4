package com.hotel.decorator;

import com.hotel.interfaces.Servicio;

public class DesayunoDecorator extends ServicioAdicional {
    private static final double COSTO_DESAYUNO = 15.0;

    public DesayunoDecorator(Servicio wrappee) {
        super(wrappee);
    }

    @Override
    public String getDescripcion() {
        return wrappee.getDescripcion() + ", con Desayuno";
    }

    @Override
    public double getCosto() {
        return wrappee.getCosto() + COSTO_DESAYUNO;
    }
}
