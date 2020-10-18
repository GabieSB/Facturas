/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dto;


import java.util.Date;
import lombok.*;
import org.una.tienda.facturacion.entities.Producto;


/**
 *
 * @author Roberth :)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoPrecioDTO {

    private Long id;
    private Double descuentoMaximo;
    private Double descuentoPromocional;
    private Boolean estado;
    private Date fechaRegistro;
    private Date fechaModificacion;
    private Double precioColones;
    private ProductoDTO productoId;
    
    
    

}
