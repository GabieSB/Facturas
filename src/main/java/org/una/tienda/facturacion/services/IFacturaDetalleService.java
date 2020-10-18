package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

import java.util.Optional;

public interface IFacturaDetalleService {

    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalleDTO) throws ProductoConDescuentoMayorAlPermitidoException;

    public FacturaDetalleDTO create2(FacturaDetalleDTO facturaDetalleDTO);

    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO);

    public void delete(Long id);

    public Optional<FacturaDetalleDTO> findById(Long id);
}
