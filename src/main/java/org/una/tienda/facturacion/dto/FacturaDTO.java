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

/**
 *
 * @author Roberth :)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacturaDTO {

    private Long id;
    private Integer caja;
    private Integer descuentoGeneral;
    private Boolean estado;
    private Date fechaRegistro;
    private Date fechaModificacion;
    private ClienteDTO clienteId;
    private List<FacturaDetalleDTO> facturaDetalleList;
}
