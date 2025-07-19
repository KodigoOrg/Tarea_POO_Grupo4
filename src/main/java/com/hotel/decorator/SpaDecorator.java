package com.hotel.decorator;

import com.hotel.interfaces.Servicio;

public class SpaDecorator extends ServicioAdicional {
    private static final double COSTO_SPA = 50.0;

    public SpaDecorator(Servicio wrappee) {
        super(wrappee);
    }

    @Override
    public String getDescripcion() {
        return wrappee.getDescripcion() + ", con Acceso a Spa";
    }

    @Override
    public double getCosto() {
        return wrappee.getCosto() + COSTO_SPA;
    }
}
