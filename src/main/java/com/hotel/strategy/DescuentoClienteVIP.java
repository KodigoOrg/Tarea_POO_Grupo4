package com.hotel.strategy;

public class DescuentoClienteVIP implements PoliticaPrecio {
    private static final double FACTOR_DESCUENTO_VIP = 0.90; // 10% de descuento

    @Override
    public double calcularPrecio(double precioBaseHabitacion, int numeroDias) {
        System.out.println("Aplicando política de precio: Cliente VIP (-" + ((1 - FACTOR_DESCUENTO_VIP) * 100) + "%)");
        return precioBaseHabitacion * FACTOR_DESCUENTO_VIP;
    }
}
