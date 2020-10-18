package org.una.tienda.facturacion.exceptions;

public class EvitarFacturarProductosPrecioCero  extends Exception{

    EvitarFacturarProductosPrecioCero(String mensaje){
        super(mensaje);
    }
}
