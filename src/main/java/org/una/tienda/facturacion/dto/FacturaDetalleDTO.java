/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.una.tienda.facturacion.entities.Producto;

/**
 *
 * @author LordLalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacturaDetalleDTO {
    private Long id;
    private boolean estado;
    private Date fechaRegistro;
    private Date fechaModificacion;
    private double cantidad;
    private double descuento_final;
    private ProductoDTO productoId;
    private FacturaDTO facturasId;


}
