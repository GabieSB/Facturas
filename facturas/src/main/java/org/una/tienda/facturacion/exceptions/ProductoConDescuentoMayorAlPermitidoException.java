/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.exceptions;

/**
 *
 * @author Ruben
 */
public class ProductoConDescuentoMayorAlPermitidoException extends Exception{
     
    public ProductoConDescuentoMayorAlPermitidoException(String message) {
        super(message);
    }
}
