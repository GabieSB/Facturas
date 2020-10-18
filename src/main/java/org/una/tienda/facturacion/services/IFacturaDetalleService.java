package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.exceptions.ProductoPrecioCeroExeption;

import java.util.Optional;

public interface IFacturaDetalleService {
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalleDTO) throws ProductoConDescuentoMayorAlPermitidoException, ProductoPrecioCeroExeption;

    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO) throws EvitarModificarContenidoInactivoExeption;

    public void delete(Long id);

    public Optional<FacturaDetalleDTO> findById(Long id);
}
