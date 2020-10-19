package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.*;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

import java.util.Optional;

public interface IFacturaDetalleService {
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalleDTO) throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCeroExeption, ProductoSinExistenciaExeption, ProductoPrecioCeroExeption;

    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO) throws EModificarContenidoInactivoExeption;

    public void delete(Long id);

    public Optional<FacturaDetalleDTO> findById(Long id);
}
