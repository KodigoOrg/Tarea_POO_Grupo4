package com.hotel.decorator;

import com.hotel.interfaces.Servicio;

public class MascotaDecorator extends ServicioAdicional {
    private static final double COSTO_MASCOTA = 25.0;

    public MascotaDecorator(Servicio wrappee) {
        super(wrappee);
    }

    @Override
    public String getDescripcion() {
        return wrappee.getDescripcion() + ", con Permiso para Mascotas";
    }

    @Override
    public double getCosto() {
        return wrappee.getCosto() + COSTO_MASCOTA;
    }
}
