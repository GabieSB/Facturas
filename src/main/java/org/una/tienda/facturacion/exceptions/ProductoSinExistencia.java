package org.una.tienda.facturacion.exceptions;

public class ProductoSinExistencia extends Exception{

    public ProductoSinExistencia(String mensaje){
        super(mensaje);
    }
}
