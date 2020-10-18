package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.exceptions.ClienteEstaInactivoExeption;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

import java.util.Optional;

public interface IFacturaService {
    public FacturaDTO create(FacturaDTO facturaDTO) throws ClienteEstaInactivoExeption;

    public Optional<FacturaDTO> update(FacturaDTO facturaDTO) throws EvitarModificarContenidoInactivoExeption;

    public void delete(Long id);

    public Optional<FacturaDTO> findById(Long id);
}
