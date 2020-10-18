/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.una.tienda.facturacion.entities.ProductoPrecio;

/**
 *
 * @author LordLalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoDTO {

    private Long id;
    private String descripcion;
    private boolean estado;
    private Date fechaRegistro;
    private Date fechaModificacion;
    private double impuesto;
    private List<ProductoPrecioDTO> productPreciotList;
    private List<FacturaDetalleDTO> facturaDetalleList;
     private List<ProductoExistenciaDTO> productExistList;

}
